package com.hos.service.validator.organisation.impl

import com.hos.service.model.common.Validation
import com.hos.service.model.entity.OrganisationEntity
import com.hos.service.model.enum.EntityStatus
import com.hos.service.model.enum.ValidationStatus
import com.hos.service.model.form.DepartmentForm
import com.hos.service.model.form.LocationForm
import com.hos.service.model.form.OrganisationForm
import com.hos.service.repository.OrganisationRepository
import com.hos.service.utils.*
import com.hos.service.validator.FormValidator
import org.springframework.stereotype.Component

@Component
class OrganisationFormValidatorImpl(
        private val organisationRepository: OrganisationRepository,
        private val departmentValidator: FormValidator<DepartmentForm, OrganisationEntity>,
        private val locationValidator: FormValidator<LocationForm, OrganisationEntity>
) : FormValidator<OrganisationForm, OrganisationEntity> {

    override fun validateInitiallyBeforeRegistration(form: OrganisationForm): Validation {
        val validation = Validation("organisationForm")

        validation.addValidation(validateForbiddenField(form.id, "id", "id"))
        validation.addValidation(validateRequiredStringWithSize(form.name, "name", 5, 50, "name"))
        validation.addValidation(validateRequiredStringWithSize(form.nip, "nip", 5, 50, "nip"))
        validation.addValidation(validateRequiredCollectionWithMinSize(form.departments, "departments", 1, "departments"))
        validation.addValidation(validateRequiredCollectionWithMinSize(form.locations, "locations", 1, "locations"))
        validation.addValidation(validateRequiredField(form.status, "status", "status"))
        if (form.status == EntityStatus.DELETED) {
            validation.addValidation(
                    "Nie można zarejestrować organizacji ze statusem ${EntityStatus.DELETED.desc}",
                    "status",
                    ValidationStatus.BLOCKER
            )
        }

        form.departments?.forEach {
            validation.addValidation(departmentValidator.validateInitiallyBeforeRegistration(it))
        }
        form.locations?.forEach {
            validation.addValidation(locationValidator.validateInitiallyBeforeRegistration(it))
        }

        return validation
    }

    override fun validateComplexBeforeRegistration(form: OrganisationForm): Validation {
        val validation = Validation("organisationForm")

        if (organisationRepository.findByNameAndNip(form.name, form.nip) != null) {
            validation.addValidation(
                    "Istnieje już organizacja o nazwie '${form.name}' i NIP: '${form.nip}'",
                    "status",
                    ValidationStatus.BLOCKER
            )
        }

        form.departments?.forEach { department ->
            if (form.departments.any { it != department && it.name == department.name }) {
                validation.addValidation(
                        "Nie można dodać dwóch działów o takich samych nazwach: '${department.name}'",
                        "departments.name",
                        ValidationStatus.BLOCKER
                )
            } else {
                validation.addValidation(departmentValidator.validateComplexBeforeRegistration(department))
            }
        }

        form.locations?.forEach { location ->
            if (form.locations.any { it != location && it.name == location.name }) {
                validation.addValidation(
                        "Nie można dodać dwóch lokalizacji o takich samych nazwach: '${location.name}'",
                        "locations.name",
                        ValidationStatus.BLOCKER
                )
            } else {
                validation.addValidation(locationValidator.validateComplexBeforeRegistration(location))
            }
        }

        return validation
    }

    override fun validateInitiallyBeforeModification(form: OrganisationForm): Validation {
        val validation = Validation("organisationForm")
        val user = getCurrentUser()

        validation.addValidation(validateRequiredField(form.id, "id", "id"))
        validation.addValidation(validateElectiveStringWithSize(form.name, "name", 5, 50, "name"))

        if (user.isAdmin()) {
            validation.addValidation(validateElectiveStringWithSize(form.nip, "nip", 5, 50, "nip"))
            validation.addValidation(validateElectiveCollectionWithMinSize(form.departments, "departments", 1, "departments"))
            validation.addValidation(validateElectiveCollectionWithMinSize(form.locations, "locations", 1, "locations"))
        } else {
            validation.addValidation(validateForbiddenField(form.nip, "nip", "nip"))
            validation.addValidation(validateForbiddenField(form.departments, "departments", "departments"))
            validation.addValidation(validateForbiddenField(form.locations, "locations", "locations"))
        }

        return validation
    }

    override fun validateComplexBeforeModification(form: OrganisationForm, entity: OrganisationEntity): Validation {
        val validation = Validation("organisationForm")

        if (form.name != null || form.nip != null) {
            val name = form.name ?: entity.name
            val nip = form.nip ?: entity.nip
            val organisationDuplication = organisationRepository.findByNameAndNip(name, nip)
            if (organisationDuplication != null && organisationDuplication.id != form.id) {
                validation.addValidation(
                        "Istnieje już organizacja o nazwie '$name' i NIP: '$nip'",
                        "status",
                        ValidationStatus.BLOCKER
                )
            }
        }

        form.departments?.forEach {
            validation.addValidation(departmentValidator.validateComplexBeforeModification(it, entity))
        }

        form.locations?.forEach {
            validation.addValidation(locationValidator.validateComplexBeforeModification(it, entity))
        }

        return validation
    }

}