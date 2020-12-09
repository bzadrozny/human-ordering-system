package com.hos.service.validator.commission.impl

import com.hos.service.model.common.Validation
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.enum.CommissionStatus
import com.hos.service.model.enum.EntityStatus
import com.hos.service.model.enum.ValidationStatus
import com.hos.service.model.form.CommissionForm
import com.hos.service.model.form.CommissionRecordForm
import com.hos.service.repository.LocationRepository
import com.hos.service.repository.OrganisationRepository
import com.hos.service.repository.UserRepository
import com.hos.service.utils.*
import com.hos.service.utils.validateRequiredField
import com.hos.service.validator.FormValidator
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class CommissionFormValidatorImpl(
        private val userRepository: UserRepository,
        private val locationRepository: LocationRepository,
        private val organisationRepository: OrganisationRepository,
        private val commissionRecordValidator: FormValidator<CommissionRecordForm, CommissionEntity>
) : FormValidator<CommissionForm, CommissionEntity> {

    override fun validateInitiallyBeforeRegistration(form: CommissionForm): Validation {
        val validation = Validation("commissionForm")

        validation.addValidation(
                validateForbiddenField(form.id, "id", "id")
        )
        validation.addValidation(
                validateRequiredField(form.principal, "zamwiający", "principal")
        )
        validation.addValidation(
                validateRequiredField(form.location, "lokalizacja", "location")
        )
        validation.addValidation(
                validateElectiveStringWithSize(form.description, "opis", 0, 255, "description")
        )
        validation.addValidation(
                validateRequiredFieldWithValue(form.status, "status", CommissionStatus.CREATED, "status")
        )
        validation.addValidation(
                validateRequiredCollectionWithSize(form.records, "rekordy", 1, 10, "records")
        )
        form.records?.forEach {
            validation.addValidation(commissionRecordValidator.validateInitiallyBeforeRegistration(it))
        }

        return validation
    }

    override fun validateComplexBeforeRegistration(form: CommissionForm): Validation {
        return validateComplexCommon(form)
    }


    override fun validateInitiallyBeforeModification(form: CommissionForm): Validation {
        val validation = Validation("commissionForm")

        validation.addValidation(
                validateRequiredField(form.id, "id", "id")
        )
        validation.addValidation(
                validateRequiredField(form.principal, "zamawiający", "principal")
        )
        validation.addValidation(
                validateRequiredField(form.location, "lokalizacja", "location")
        )
        validation.addValidation(
                validateElectiveStringWithSize(form.description, "opis", 0, 255, "description")
        )

        //TODO prepare support for editing records with commission modification
        validation.addValidation(
                validateForbiddenField(form.records, "rekordy", "records")
        )
        return validation
    }

    override fun validateComplexBeforeModification(form: CommissionForm, entity: CommissionEntity): Validation {
        val validation = Validation("commissionForm")
        val user = getCurrentUser()

        if (user.isAdmin()) {
            if (entity.status in listOf(CommissionStatus.DELETED, CommissionStatus.COMPLETED)) {
                validation.addValidation(
                        "Brak możliwości edycji zamkniętego zamówienia",
                        "status",
                        ValidationStatus.BLOCKER
                )
            }
        } else if (entity.status !in listOf(CommissionStatus.CREATED, CommissionStatus.MODIFIED)) {
            validation.addValidation(
                    "Brak możliwości edycji procesowanego lub zakończonego zamówienia",
                    "status",
                    ValidationStatus.BLOCKER
            )
        }

        val availableStatuses = mutableListOf(entity.status)
                .let {
                    if (user.isAdmin())
                        it.add(CommissionStatus.DELETED)
                    it
                }
        validation.addValidation(validateRequiredFieldFromCollection(
                form.status,
                "status",
                availableStatuses,
                "status"
        ))


        if (!validation.hasBlocker()) {
            validation.addValidation(validateComplexCommon(form))
        }

        return validation
    }

    private fun validateComplexCommon(form: CommissionForm): Validation {
        val validation = Validation("commissionForm")
        val user = getCurrentUser()

        if (user.isAdmin()) {
            val location = locationRepository.findByIdOrNull(form.location)
            if (location == null || location.status != EntityStatus.ACTIVE) {
                validation.addValidation(
                        "Zamówienia można składać jedynie do aktywnych lokalizacji",
                        "location",
                        ValidationStatus.BLOCKER
                )
            }

            val principal = userRepository.findByIdOrNull(form.principal)
            if (principal == null) {
                validation.addValidation(
                        "Wskazany użytkownik nie istnieje",
                        "principal",
                        ValidationStatus.BLOCKER
                )

            } else if (!user.isAdmin() && principal.status != EntityStatus.ACTIVE) {
                validation.addValidation(
                        "Zamówienie klienta może być przypisane jedynie do aktywnego użytkownika",
                        "principal",
                        ValidationStatus.BLOCKER
                )
            } else if (!principal.isAdmin() && principal.organisation != location?.organisation) {
                validation.addValidation(
                        "Zamówienie klienta może być przypisane jedynie lokalizacji z tej samej organizacji",
                        "principal",
                        ValidationStatus.BLOCKER
                )
            }
        } else {
            val organisation = organisationRepository.getOne(user.organisation!!)
            val location = organisation.locations.firstOrNull { it.id == form.location }
            if (location == null) {
                validation.addValidation(
                        "Użytkownik nie jest uprawniony do dodania zamówienia do lokalizacji spoza swojej organizacji",
                        "user.authority",
                        ValidationStatus.BLOCKER
                )
            } else if (location.status != EntityStatus.ACTIVE) {
                validation.addValidation(
                        "Zamówienia można składać jedynie do aktywnych lokalizacji",
                        "location",
                        ValidationStatus.BLOCKER
                )
            }

            if (user.id != form.principal) {
                validation.addValidation(
                        "Klient może składać zamówienia jedynie w swoim własnym imieniu",
                        "principal",
                        ValidationStatus.BLOCKER
                )
            }
        }
        return validation
    }


}