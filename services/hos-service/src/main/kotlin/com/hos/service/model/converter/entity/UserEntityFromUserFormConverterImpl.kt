package com.hos.service.model.converter.entity

import com.hos.service.model.converter.Converter
import com.hos.service.model.entity.AuthorityRoleEntity
import com.hos.service.model.entity.UserEntity
import com.hos.service.model.form.UserForm
import com.hos.service.repo.DepartmentRepository
import com.hos.service.repo.LocationRepository
import com.hos.service.repo.OrganisationRepository
import com.hos.service.repo.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserEntityFromUserFormConverterImpl(
        private val userRepository: UserRepository,
        private val organisationRepository: OrganisationRepository
) : Converter<UserForm, UserEntity> {

    override fun create(source: UserForm): UserEntity {
        val organisation = organisationRepository.getOne(source.organisation!!)
        val target = UserEntity(
                login = source.login!!,
                email = source.email!!,
                password = source.password!!,
                name = source.name!!,
                surname = source.surname!!,
                phone1 = source.phone1!!,
                phone2 = source.phone2,
                superior = source.superior?.let { userRepository.getOne(it) },
                organisation = organisation,
                department = organisation.departments.first { it.id == source.department },
                location = organisation.locations.first { it.id == source.location }
        )
        source.authorities?.forEach {
            target.authorities.add(AuthorityRoleEntity(
                    user = target,
                    role = it
            ))
        }
        return target;
    }

    override fun merge(source: UserForm, target: UserEntity): UserEntity {
        source.login?.let { target.login = it }
        source.email?.let { target.email = it }
        source.password?.let { target.password = it }
        source.name?.let { target.name = it }
        source.surname?.let { target.surname = it }
        source.phone1?.let { target.phone1 = it }
        source.phone2?.let { target.phone2 = it }

        source.superior?.let {
            if (target.superior?.id != it) target.superior = userRepository.getOne(it)
        }
        source.department?.let { departmentId ->
            if (target.department.id != departmentId)
                target.department = target.organisation.departments.first { it.id == departmentId }
        }
        source.location?.let { locationId ->
            if (target.location.id != locationId)
                target.location = target.organisation.locations.first { it.id == locationId }
        }

        source.authorities?.let { authorities ->
            target.authorities.removeIf { !authorities.contains(it.role) }
            target.authorities.addAll(
                    authorities.filter { !target.authorities.map(AuthorityRoleEntity::role).contains(it) }
                            .map { AuthorityRoleEntity(user = target, role = it) }
            )
        }

        return target
    }

}