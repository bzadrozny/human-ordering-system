package com.hos.service.model.validator.impl

import com.hos.service.model.common.Validation
import com.hos.service.model.entity.AuthorityRoleEntity
import com.hos.service.model.entity.UserEntity
import com.hos.service.model.enum.Authority
import com.hos.service.model.enum.EntityStatus
import com.hos.service.model.enum.ValidationStatus
import com.hos.service.model.form.UserForm
import com.hos.service.model.validator.FormValidator
import com.hos.service.repo.OrganisationRepository
import com.hos.service.repo.UserRepository
import com.hos.service.security.UserDetailsContainer
import com.hos.service.utils.*
import com.hos.service.utils.validateRequiredStringWithSize
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFormValidatorImpl(
        private val userRepository: UserRepository,
        private val organisationRepository: OrganisationRepository,
        private val authorityValidator: FormValidator<Authority, AuthorityRoleEntity>
) : FormValidator<UserForm, UserEntity> {

    override fun validateBeforeRegistration(form: UserForm): Validation {
        val validation = Validation("UserForm")
        val principal = SecurityContextHolder.getContext()?.authentication?.principal as UserDetailsContainer
        val isAdmin = principal.authorities.any { it.authority == Authority.ADMIN.name }
        val isDirector = principal.authorities.any { it.authority == Authority.DIRECTOR.name }
        val isManager = principal.authorities.any { it.authority == Authority.MANAGER.name }

        if (!isAdmin && !isDirector && !isManager) {
            validation.addValidation(
                    "Użytkownik nie jest uprawniony do rejestracji nowych kont",
                    "user.authorities",
                    ValidationStatus.BLOCKER
            )
            return validation
        }

        validation.addValidation(validateForbiddenField(form.id, "login", "userForm.id"))
        validation.addValidation(validateRequiredStringWithSize(form.login, "login", 5, 50, "userForm.login"))
        validation.addValidation(validateRequiredStringWithSize(form.email, "email", 6, 50, "userForm.email"))
        validation.addValidation(validateRequiredStringWithSize(form.password, "password", 8, 20, "userForm.password"))
        validation.addValidation(validateRequiredField(form.status, "status", "userForm.status"))
        validation.addValidation(validateRequiredStringWithSize(form.name, "name", 2, 20, "userForm.name"))
        validation.addValidation(validateRequiredStringWithSize(form.surname, "surname", 2, 20, "userForm.surname"))
        validation.addValidation(validateRequiredStringWithSize(form.phone1, "phone1", 8, 20, "userForm.phone1"))
        validation.addValidation(validateElectiveStringWithSize(form.phone2, "phone2", 8, 20, "userForm.phone2"))
        validation.addValidation(validateRequiredField(form.department, "department", "userForm.department"))
        validation.addValidation(validateRequiredField(form.location, "location", "userForm.location"))
        validation.addValidation(validateRequiredFieldWarning(form.superior, "superior", "userForm.superior"))

        validation.addValidation(validateRequiredCollectionWithSize(form.authorities, "authorities", 1, 5, "userForm.authorities"))
        form.authorities?.forEach {
            validation.addValidation(authorityValidator.validateBeforeRegistration(it))
        }

        if (validation.hasBlocker()) {
            return validation
        }

        userRepository.findByLoginOrEmail(form.login!!, form.email!!)?.let {
            validation.addValidation(
                    "Istnieje użytkownik o podanym loginie lub adresie email",
                    "userForm.login|userForm.email",
                    ValidationStatus.BLOCKER
            )
        }

        if (form.status == EntityStatus.DELETED) {
            validation.addValidation(
                    "Nie można zarejestrować użytkownika ze statusem ${EntityStatus.DELETED.desc}",
                    "userForm.status",
                    ValidationStatus.BLOCKER
            )
        }

        val organisation = organisationRepository.findByIdOrNull(form.organisation)
        if (organisation == null) {
            validation.addValidation(
                    "Nie istnieje organizacja o podanym dziale oraz lokalizacji",
                    "userForm.organisation",
                    ValidationStatus.BLOCKER
            )
        } else {
            if (principal.organisation != organisation.id && form.authorities!!.any { it != Authority.CLIENT }) {
                validation.addValidation(
                        "Konta z innych organizacji mogą posiadać jedynie uprawnienia klienckie",
                        "userForm.organisation",
                        ValidationStatus.BLOCKER
                )
            }
            if (organisation.departments.none { it.id == form.department }) {
                validation.addValidation(
                        "Dział o podanym identyfikatorze nie jest dostępny dla organizacji danego konta",
                        "userForm.department",
                        ValidationStatus.BLOCKER
                )
            }
            if (organisation.locations.none { it.id == form.location }) {
                validation.addValidation(
                        "Lokalizacja o podanym identyfikatorze nie jest dostępna dla organizacji danego konta",
                        "userForm.location",
                        ValidationStatus.BLOCKER
                )
            }
        }

        form.superior?.let {
            val superior = userRepository.findByIdOrNull(it)
            if (superior == null) {
                validation.addValidation(
                        "Nie istnieje użytkownik o podanym identyfikatorze",
                        "userForm.superior",
                        ValidationStatus.BLOCKER
                )
            } else if (superior.location.organisation != organisation) {
                validation.addValidation(
                        "Wprowadzany użytkownik musi być z tej samej organizacji co przełożony",
                        "userForm.superior",
                        ValidationStatus.BLOCKER
                )
            }
        }

        return validation
    }

    override fun validateInitiallyModification(form: UserForm): Validation {
        val validation = Validation("UserForm")
        val principal = SecurityContextHolder.getContext()?.authentication?.principal as UserDetailsContainer
        val isAdmin = principal.authorities.any { it.authority == Authority.ADMIN.name }
        val isDirector = principal.authorities.any { it.authority == Authority.DIRECTOR.name }
        val isManager = principal.authorities.any { it.authority == Authority.MANAGER.name }

        if (principal.id != form.id && !isAdmin && !isDirector && !isManager) {
            validation.addValidation(
                    "Użytkownik nie jest uprawniony do modyfikacji danych konta",
                    "userForm.authorities",
                    ValidationStatus.BLOCKER
            )
            return validation
        }

        validation.addValidation(validateRequiredField(form.id, "id", "userForm.id"))
        if (!isAdmin && !isDirector) {
            validation.addValidation(validateForbiddenField(form.authorities, "authorities", "userForm.authorities"))
            validation.addValidation(validateForbiddenField(form.department, "department", "userForm.department"))
            validation.addValidation(validateForbiddenField(form.location, "location", "userForm.location"))
            validation.addValidation(validateForbiddenField(form.superior, "superior", "userForm.superior"))
        }
        if (!isAdmin && !isDirector && !isManager) {
            validation.addValidation(validateForbiddenField(form.status, "status", "userForm.authorities"))
        }

        return validation
    }

    override fun validateBeforeRegisterModification(form: UserForm, entity: UserEntity): Validation {
        val validation = Validation("UserForm")
        val principal = SecurityContextHolder.getContext()?.authentication?.principal as UserDetailsContainer
        val userIsAdmin = principal.authorities.any { it.authority == Authority.ADMIN.name }
        val userIsClient = principal.authorities.any { it.authority == Authority.CLIENT.name }
        val entityIsClient = entity.authorities.any { it.role == Authority.CLIENT }

        if (userIsAdmin) {
            validation.addValidation(validateElectiveStringWithSize(form.login, "login", 5, 50, "userForm.login"))
        } else {
            validation.addValidation(validateForbiddenField(form.login, "login", "userForm.login"))
            validation.addValidation(validateElectiveFieldFromCollection(form.status, "status", listOf(EntityStatus.ACTIVE, EntityStatus.DISABLED), "userForm.status"))
        }

        if (principal.id == form.id || (entityIsClient && !userIsClient) || userIsAdmin) {
            validation.addValidation(validateElectiveStringWithSize(form.email, "email", 6, 50, "userForm.email"))
            validation.addValidation(validateElectiveStringWithSize(form.password, "password", 8, 20, "userForm.password"))
            validation.addValidation(validateElectiveStringWithSize(form.name, "name", 2, 20, "userForm.name"))
            validation.addValidation(validateElectiveStringWithSize(form.surname, "surname", 2, 20, "userForm.surname"))
            validation.addValidation(validateElectiveStringWithSize(form.phone1, "phone1", 8, 20, "userForm.phone1"))
            validation.addValidation(validateElectiveStringWithSize(form.phone2, "phone2", 8, 20, "userForm.phone2"))
        } else {
            validation.addValidation(validateForbiddenField(form.email, "email", "userForm.email"))
            validation.addValidation(validateForbiddenField(form.password, "password", "userForm.password"))
            validation.addValidation(validateForbiddenField(form.name, "name", "userForm.name"))
            validation.addValidation(validateForbiddenField(form.surname, "surname", "userForm.surname"))
            validation.addValidation(validateForbiddenField(form.phone1, "phone1", "userForm.phone1"))
            validation.addValidation(validateForbiddenField(form.phone2, "phone2", "userForm.phone2"))
        }

        if (validation.hasBlocker()) {
            return validation
        }

        form.login?.let {
            if (form.login != entity.login) userRepository.findByLogin(form.login)
                    ?: validation.addValidation(
                            "Istnieje użytkownik o podanym loginie",
                            "userForm.login",
                            ValidationStatus.BLOCKER
                    )
        }
        form.email?.let {
            if (form.email != entity.email) userRepository.findByLogin(form.email)
                    ?: validation.addValidation(
                            "Istnieje użytkownik o podanym adresie email",
                            "userForm.email",
                            ValidationStatus.BLOCKER
                    )
        }

        form.superior?.let {
            if (form.superior != entity.superior?.id) {
                val superior = userRepository.findByIdOrNull(it)
                if (superior == null) {
                    validation.addValidation(
                            "Nie istnieje użytkownik o podanym identyfikatorze",
                            "userForm.superior",
                            ValidationStatus.BLOCKER
                    )
                } else if (superior.organisation != entity.organisation) {
                    validation.addValidation(
                            "Wprowadzany użytkownik musi być z tej samej organiezacji co przełożony",
                            "userForm.superior",
                            ValidationStatus.BLOCKER
                    )
                }
            }
        }

        form.location?.let {
            if (form.location != entity.location.id) {
                entity.organisation
                        .locations
                        .firstOrNull { locationEntity -> locationEntity.id == it }
                        ?: validation.addValidation(
                                "Lokalizacja o podanym identyfikatorze nie jest dostępna dla organizacji danego konta",
                                "userForm.location",
                                ValidationStatus.BLOCKER
                        )
            }
        }

        form.department?.let { departmentId ->
            if (form.department != entity.department.id) {
                entity.organisation
                        .departments
                        .firstOrNull { it.id == departmentId }
                        ?: validation.addValidation(
                                "Dział o podanym identyfikatorze nie jest dostępny dla organizacji danego konta",
                                "userForm.department",
                                ValidationStatus.BLOCKER
                        )
            }
        }

        return validation
    }

}