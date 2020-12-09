package com.hos.service.validator.user.impl

import com.hos.service.model.common.Validation
import com.hos.service.model.entity.AuthorityRoleEntity
import com.hos.service.model.enum.Authority
import com.hos.service.model.enum.ValidationStatus
import com.hos.service.validator.FormValidator
import com.hos.service.security.UserDetailsContainer
import com.hos.service.utils.getCurrentUser
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthoritiesFormValidatorImpl : FormValidator<Authority, AuthorityRoleEntity> {

    override fun validateInitiallyBeforeRegistration(form: Authority): Validation {
        val validation = Validation("authority")
        val user = getCurrentUser()

        if (form == Authority.ADMIN && !user.isAdmin()) {
            validation.addValidation(
                    "Użytkownik nie jest uprawniony nadania uprawnień Administratora",
                    "user.authorities",
                    ValidationStatus.BLOCKER
            )
        }
        if (form == Authority.DIRECTOR && !user.isAdmin()) {
            validation.addValidation(
                    "Użytkownik nie jest uprawniony nadania uprawnień Dyrektorskich",
                    "user.authorities",
                    ValidationStatus.BLOCKER
            )
        }
        if (form == Authority.MANAGER && !user.isAdmin() && !user.isDirector()) {
            validation.addValidation(
                    "Użytkownik nie jest uprawniony nadania uprawnień Managerskich",
                    "user.authorities",
                    ValidationStatus.BLOCKER
            )
        }

        return validation
    }

    override fun validateComplexBeforeRegistration(form: Authority): Validation {
        return Validation("authority")
    }

    override fun validateInitiallyBeforeModification(form: Authority): Validation {
        return validateInitiallyBeforeRegistration(form)
    }

    override fun validateComplexBeforeModification(form: Authority, entity: AuthorityRoleEntity): Validation {
        return Validation("authority")
    }

}