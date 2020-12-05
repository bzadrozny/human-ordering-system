package com.hos.service.model.converter.entity

import com.hos.service.model.converter.Converter
import com.hos.service.model.entity.AuthorityRoleEntity
import com.hos.service.model.entity.UserEntity
import com.hos.service.model.form.user.UserForm
import com.hos.service.repo.OrganisationRepository
import com.hos.service.repo.UserRepository
import org.springframework.stereotype.Component

@Component
class UserEntityFromUserFormConverterImpl(
        private val userRepository: UserRepository,
        private val organisationRepository: OrganisationRepository
) : Converter<UserForm, UserEntity> {

    override fun create(source: UserForm): UserEntity {
        val personal = source.personal!!
        val administrative = source.administrative!!

        val organisation = organisationRepository.getOne(administrative.organisation!!)
        val target = UserEntity(
                login = source.login!!,
                password = source.password!!,
                status = source.status!!,
                name = personal.name!!,
                surname = personal.surname!!,
                phone1 = personal.phone1!!,
                phone2 = personal.phone2,
                email = personal.email!!,
                superior = administrative.superior?.let { userRepository.getOne(it) },
                organisation = organisation,
                department = organisation.departments.first { it.id == administrative.department },
                location = organisation.locations.first { it.id == administrative.location },
        )
        administrative.authorities?.forEach {
            target.authorities.add(AuthorityRoleEntity(
                    user = target,
                    role = it
            ))
        }
        return target;
    }

    override fun merge(source: UserForm, target: UserEntity): UserEntity {
        source.login?.let { target.login = it }
        source.password?.let { target.password = it }
        source.status?.let { target.status = it }

        source.personal?.let {
            target.name = it.name!!
            target.surname = it.surname!!
            target.phone1 = it.phone1!!
            target.phone2 = it.phone2!!
            target.email = it.email!!
        }

        source.administrative?.let { administrative ->
            administrative.superior?.let {
                if (target.superior?.id != it) target.superior = userRepository.getOne(it)
            }
            administrative.department?.let { departmentId ->
                if (target.department.id != departmentId)
                    target.department = target.organisation.departments.first { it.id == departmentId }
            }
            administrative.location?.let { locationId ->
                if (target.location.id != locationId)
                    target.location = target.organisation.locations.first { it.id == locationId }
            }
            administrative.authorities?.let { authorities ->
                target.authorities.removeIf { !authorities.contains(it.role) }
                target.authorities.addAll(
                        authorities.filter { !target.authorities.map(AuthorityRoleEntity::role).contains(it) }
                                .map { AuthorityRoleEntity(user = target, role = it) }
                )
            }
        }

        return target
    }

}