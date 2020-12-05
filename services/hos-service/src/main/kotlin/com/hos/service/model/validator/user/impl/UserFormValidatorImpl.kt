package com.hos.service.model.validator.user.impl

import com.hos.service.model.common.Validation
import com.hos.service.model.entity.AuthorityRoleEntity
import com.hos.service.model.entity.UserEntity
import com.hos.service.model.enum.Authority
import com.hos.service.model.enum.EntityStatus
import com.hos.service.model.enum.ValidationStatus
import com.hos.service.model.form.user.UserForm
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

        validation.addValidation(validateRequiredField(form.personal, "personal", "personal"))
        validation.addValidation(validateRequiredField(form.administrative, "administrative", "administrative"))

        if (validation.hasBlocker()) {
            return validation
        }

        validation.addValidation(validateForbiddenField(form.id, "login", "id"))
        validation.addValidation(validateRequiredField(form.status, "status", "status"))
        validation.addValidation(validateRequiredStringWithSize(form.login, "login", 5, 50, "login"))
        validation.addValidation(validateRequiredStringWithSize(form.password, "password", 8, 20, "password"))

        val personal = form.personal!!
        validation.addValidation(validateRequiredStringWithSize(personal.name, "name", 2, 20, "personal.name"))
        validation.addValidation(validateRequiredStringWithSize(personal.surname, "surname", 2, 20, "personal.surname"))
        validation.addValidation(validateRequiredStringWithSize(personal.phone1, "phone1", 8, 20, "personal.phone1"))
        validation.addValidation(validateElectiveStringWithSize(personal.phone2, "phone2", 8, 20, "personal.phone2"))
        validation.addValidation(validateRequiredStringWithSize(personal.email, "email", 6, 50, "personal.email"))

        val administrative = form.administrative!!
        validation.addValidation(validateRequiredField(administrative.department, "department", "administrative.department"))
        validation.addValidation(validateRequiredField(administrative.location, "location", "administrative.location"))
        validation.addValidation(validateRequiredFieldWarning(administrative.superior, "superior", "administrative.superior"))
        validation.addValidation(validateRequiredCollectionWithSize(administrative.authorities, "authorities", 1, 5, "administrative.authorities"))
        administrative.authorities?.forEach {
            validation.addValidation(authorityValidator.validateBeforeRegistration(it))
        }

        if (validation.hasBlocker()) {
            return validation
        }

        if (form.status == EntityStatus.DELETED) {
            validation.addValidation(
                    "Nie można zarejestrować użytkownika ze statusem ${EntityStatus.DELETED.desc}",
                    "status",
                    ValidationStatus.BLOCKER
            )
        }

        userRepository.findByLoginOrEmail(form.login!!, personal.email!!)?.let {
            validation.addValidation(
                    "Istnieje użytkownik o podanym loginie lub adresie email",
                    "systemic.login|systemic.email",
                    ValidationStatus.BLOCKER
            )
        }

        val organisation = organisationRepository.findByIdOrNull(administrative.organisation)
        if (organisation == null) {
            validation.addValidation(
                    "Nie istnieje organizacja o podanym dziale oraz lokalizacji",
                    "administrative.organisation",
                    ValidationStatus.BLOCKER
            )
        } else {
            if (principal.organisation != organisation.id && administrative.authorities!!.any { it != Authority.CLIENT }) {
                validation.addValidation(
                        "Konta z innych organizacji mogą posiadać jedynie uprawnienia klienckie",
                        "administrative.organisation",
                        ValidationStatus.BLOCKER
                )
            }
            if (organisation.departments.none { it.id == administrative.department }) {
                validation.addValidation(
                        "Dział o podanym identyfikatorze nie jest dostępny dla organizacji danego konta",
                        "administrative.department",
                        ValidationStatus.BLOCKER
                )
            }
            if (organisation.locations.none { it.id == administrative.location }) {
                validation.addValidation(
                        "Lokalizacja o podanym identyfikatorze nie jest dostępna dla organizacji danego konta",
                        "administrative.location",
                        ValidationStatus.BLOCKER
                )
            }
        }

        administrative.superior?.let {
            val superior = userRepository.findByIdOrNull(it)
            if (superior == null) {
                validation.addValidation(
                        "Nie istnieje użytkownik o podanym identyfikatorze",
                        "administrative.superior",
                        ValidationStatus.BLOCKER
                )
            } else if (superior.location.organisation != organisation) {
                validation.addValidation(
                        "Wprowadzany użytkownik musi być z tej samej organizacji co przełożony",
                        "administrative.superior",
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

        validation.addValidation(validateRequiredField(form.id, "id", "id"))
        if (!isAdmin && !isDirector && !isManager) {
            validation.addValidation(validateForbiddenField(form.status, "status", "status"))
        }
        if (!isAdmin && !isDirector) {
            validation.addValidation(validateForbiddenField(form.administrative, "administrative", "administrative"))
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
            validation.addValidation(validateElectiveStringWithSize(form.login, "login", 5, 50, "login"))
        } else {
            validation.addValidation(validateForbiddenField(form.login, "login", "login"))
            validation.addValidation(validateElectiveFieldFromCollection(form.status, "status", listOf(EntityStatus.ACTIVE, EntityStatus.DISABLED), "status"))
        }

        if (principal.id == form.id || (entityIsClient && !userIsClient) || userIsAdmin) {
            validation.addValidation(validateElectiveStringWithSize(form.password, "password", 8, 20, "password"))
            form.personal?.let {
                validation.addValidation(validateElectiveStringWithSize(it.name, "name", 2, 20, "personal.name"))
                validation.addValidation(validateElectiveStringWithSize(it.surname, "surname", 2, 20, "personal.surname"))
                validation.addValidation(validateElectiveStringWithSize(it.phone1, "phone1", 8, 20, "personal.phone1"))
                validation.addValidation(validateElectiveStringWithSize(it.phone2, "phone2", 8, 20, "personal.phone2"))
                validation.addValidation(validateElectiveStringWithSize(it.email, "email", 6, 50, "personal.email"))
            }
        } else {
            validation.addValidation(validateForbiddenField(form.password, "password", "userForm.password"))
            form.personal?.let {
                validation.addValidation(validateForbiddenField(it.name, "name", "personal.name"))
                validation.addValidation(validateForbiddenField(it.surname, "surname", "personal.surname"))
                validation.addValidation(validateForbiddenField(it.phone1, "phone1", "personal.phone1"))
                validation.addValidation(validateForbiddenField(it.phone2, "phone2", "personal.phone2"))
                validation.addValidation(validateForbiddenField(it.email, "email", "personal.email"))
            }
        }

        if (validation.hasBlocker()) {
            return validation
        }

        form.login?.let {
            if (it != entity.login) userRepository.findByLogin(it)
                    ?: validation.addValidation(
                            "Istnieje użytkownik o podanym loginie",
                            "login",
                            ValidationStatus.BLOCKER
                    )
        }
        form.personal?.email?.let {
            if (it != entity.email) userRepository.findByLogin(it)
                    ?: validation.addValidation(
                            "Istnieje użytkownik o podanym adresie email",
                            "personal.email",
                            ValidationStatus.BLOCKER
                    )
        }

        form.administrative?.let { administrative ->
            administrative.superior?.let {
                if (it != entity.superior?.id) {
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
            administrative.location?.let {
                if (it != entity.location.id) {
                    entity.organisation.locations
                            .firstOrNull { locationEntity -> it == locationEntity.id }
                            ?: validation.addValidation(
                                    "Lokalizacja o podanym identyfikatorze nie jest dostępna dla organizacji danego konta",
                                    "administrative.location",
                                    ValidationStatus.BLOCKER
                            )
                }
            }
            administrative.department?.let {
                if (it != entity.department.id) {
                    entity.organisation.departments
                            .firstOrNull { departmentEntity -> it == departmentEntity.id }
                            ?: validation.addValidation(
                                    "Dział o podanym identyfikatorze nie jest dostępny dla organizacji danego konta",
                                    "userForm.department",
                                    ValidationStatus.BLOCKER
                            )
                }
            }
        }

        return validation
    }

}