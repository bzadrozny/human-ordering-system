package com.hos.service.validator.organisation.impl

import com.hos.service.model.common.Validation
import com.hos.service.model.entity.OrganisationEntity
import com.hos.service.model.enum.Authority
import com.hos.service.model.enum.EntityStatus
import com.hos.service.model.enum.ValidationStatus
import com.hos.service.model.form.DepartmentForm
import com.hos.service.security.UserDetailsContainer
import com.hos.service.utils.*
import com.hos.service.validator.FormValidator
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class DepartmentFormValidatorImpl : FormValidator<DepartmentForm, OrganisationEntity> {

    override fun validateInitiallyBeforeRegistration(form: DepartmentForm): Validation {
        val validation = Validation("departmentForm")

        validation.addValidation(validateForbiddenField(form.id, "id", "id"))
        validation.addValidation(validateRequiredStringWithSize(form.name, "name", 5, 50, "name"))

        validation.addValidation(validateRequiredField(form.status, "status", "status"))
        if (form.status == EntityStatus.DELETED)
            validation.addValidation(
                    "Nie można zarejestrować działu ze statusem ${EntityStatus.DELETED.desc}",
                    "status",
                    ValidationStatus.BLOCKER
            )

        return validation
    }

    override fun validateComplexBeforeRegistration(form: DepartmentForm): Validation {
        return Validation("departmentForm")
    }

    override fun validateInitiallyBeforeModification(form: DepartmentForm): Validation {
        val validation = Validation("departmentForm")

        validation.addValidation(validateRequiredField(form.id, "id", "id"))
        validation.addValidation(validateRequiredField(form.organisation, "organisation", "organisation"))
        validation.addValidation(validateElectiveStringWithSize(form.name, "name", 5, 50, "name"))

        val user = getCurrentUser()
        if (!user.isAdmin()) validation.addValidation(
                validateElectiveFieldFromCollection(
                        form.status,
                        "status",
                        listOf(EntityStatus.ACTIVE, EntityStatus.DISABLED),
                        "status"
                )
        )

        return validation
    }

    override fun validateComplexBeforeModification(form: DepartmentForm, entity: OrganisationEntity): Validation {
        val validation = Validation("departmentForm")

        if (form.id != null && entity.departments.none { it.id == form.id })
            validation.addValidation(
                    "Dla organizacji: '${entity.name}' nie istnieje dział o id: '${form.id}'",
                    "name",
                    ValidationStatus.BLOCKER
            )

        if (entity.departments.any { it.id != form.id && it.name == form.name })
            validation.addValidation(
                    "Nie mogą istnieć działy o takich samych nazwach: '${form.name}' w ramach jednej organizacji",
                    "name",
                    ValidationStatus.BLOCKER
            )

        return validation
    }

}