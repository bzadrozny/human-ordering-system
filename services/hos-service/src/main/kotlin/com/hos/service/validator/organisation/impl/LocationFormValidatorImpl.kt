package com.hos.service.validator.organisation.impl

import com.hos.service.model.common.Validation
import com.hos.service.model.entity.AddressEntity
import com.hos.service.model.entity.OrganisationEntity
import com.hos.service.model.enum.Authority
import com.hos.service.model.enum.EntityStatus
import com.hos.service.model.enum.ValidationStatus
import com.hos.service.model.form.AddressForm
import com.hos.service.model.form.LocationForm
import com.hos.service.security.UserDetailsContainer
import com.hos.service.utils.*
import com.hos.service.validator.FormValidator
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class LocationFormValidatorImpl(
        private val addressValidator: FormValidator<AddressForm, AddressEntity>
) : FormValidator<LocationForm, OrganisationEntity> {

    override fun validateInitiallyBeforeRegistration(form: LocationForm): Validation {
        val validation = Validation("locationForm")

        validation.addValidation(validateForbiddenField(form.id, "id", "id"))
        validation.addValidation(validateRequiredStringWithSize(form.name, "name", 5, 50, "name"))
        validation.addValidation(validateRequiredField(form.registeredOffice, "registered office", "registeredOffice"))
        validation.addValidation(validateRequiredField(form.address, "address", "address"))
        validation.addValidation(validateRequiredField(form.status, "status", "status"))
        if (form.status == EntityStatus.DELETED) {
            validation.addValidation(
                    "Nie można zarejestrować lokalizacji ze statusem ${EntityStatus.DELETED.desc}",
                    "status",
                    ValidationStatus.BLOCKER
            )
        }

        form.address?.let {
            validation.addValidation(addressValidator.validateInitiallyBeforeRegistration(it))
        }

        return validation
    }

    override fun validateComplexBeforeRegistration(form: LocationForm): Validation {
        return Validation("departmentForm")
    }

    override fun validateInitiallyBeforeModification(form: LocationForm): Validation {
        val validation = Validation("locationForm")

        validation.addValidation(validateRequiredField(form.id, "id", "id"))
        validation.addValidation(validateRequiredField(form.organisation, "organisation", "organisation"))
        validation.addValidation(validateElectiveStringWithSize(form.name, "name", 5, 50, "name"))

        form.address?.let {
            validation.addValidation(addressValidator.validateInitiallyBeforeModification(it))
        }

        val user = getCurrentUser()
        if (!user.isAdmin()) validation.addValidation(
                validateElectiveFieldFromCollection(
                        form.status,
                        "status",
                        listOf(EntityStatus.ACTIVE, EntityStatus.DISABLED), "" +
                        "status"
                )
        )

        return validation
    }

    override fun validateComplexBeforeModification(form: LocationForm, entity: OrganisationEntity): Validation {
        val validation = Validation("locationForm")

        if (form.id != null && entity.locations.none { it.id == form.id })
            validation.addValidation(
                    "Dla organizacji: '${entity.name}' nie istnieje lokalizacja o id: '${form.id}'",
                    "name",
                    ValidationStatus.BLOCKER
            )

        if (entity.locations.any { it.id != form.id && it.name == form.name })
            validation.addValidation(
                    "Nie mogą istnieć lokalizacje o takich samych nazwach: '${form.name}' w ramach jednej organizacji",
                    "name",
                    ValidationStatus.BLOCKER
            )

        return validation
    }

}