package com.hos.service.validator.commission.impl

import com.hos.service.model.common.Validation
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.enum.CommissionRecordStatus
import com.hos.service.model.enum.CommissionStatus
import com.hos.service.model.enum.ValidationStatus
import com.hos.service.model.form.CommissionRecordForm
import com.hos.service.security.UserDetailsContainer
import com.hos.service.utils.*
import com.hos.service.validator.FormValidator
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class CommissionRecordFormValidatorImpl : FormValidator<CommissionRecordForm, CommissionEntity> {

    override fun validateInitiallyBeforeRegistration(form: CommissionRecordForm): Validation {
        val validation = Validation("records")
        val user = getCurrentUser()

        validation.addValidation(validateForbiddenField(form.id, "id", "id"))
        validation.addValidation(validateRequiredNumberInRange(
                form.ordered,
                "zamówiona ilość",
                1.0,
                if (user.isAdmin()) 10000.0 else 200.0,
                "ordered"
        ))
        validation.addValidation(validateInitiallyCommon(form))
        validation.addValidation(validateRequiredFieldWithValue(
                form.status,
                "status",
                CommissionRecordStatus.CREATED,
                "status"
        ))

        return validation
    }

    override fun validateComplexBeforeRegistration(form: CommissionRecordForm): Validation {
        return Validation("records")
    }

    override fun validateInitiallyBeforeModification(form: CommissionRecordForm): Validation {
        val validation = Validation("records")

        validation.addValidation(validateRequiredField(form.id, "id", "id"))
        validation.addValidation(validateRequiredField(form.commission, "zamówienie", "commission"))
        validation.addValidation(validateInitiallyCommon(form))

        return validation
    }

    override fun validateComplexBeforeModification(form: CommissionRecordForm, entity: CommissionEntity): Validation {
        val validation = Validation("records")
        val user = getCurrentUser()
        if (user.isAdmin()) {
            if (entity.status in listOf(CommissionStatus.DELETED, CommissionStatus.REJECTED, CommissionStatus.COMPLETED)) {
                validation.addValidation(
                        "Brak możliwości edycji odrzuconego lub zamkniętego zamówienia",
                        "",
                        ValidationStatus.BLOCKER
                )
            }
        } else if (entity.status !in listOf(CommissionStatus.CREATED, CommissionStatus.MODIFIED)) {
            validation.addValidation(
                    "Brak możliwości edycji procesowanego lub zamkniętego zamówienia",
                    "",
                    ValidationStatus.BLOCKER
            )
        }
        if (validation.hasBlocker()) return validation

        if (form.id == null)
            validation.addValidation(validateComplexBeforeModificationNew(entity))
        else
            validation.addValidation(validateComplexBeforeModificationExisted(form, entity))


        return validation
    }

}

fun validateInitiallyCommon(form: CommissionRecordForm): Validation {
    val validation = Validation("records")
    validation.addValidation(
            validateRequiredStringWithSize(form.jobName, "nazwa stanowiska", 5, 20, "jobName")
    )
    validation.addValidation(
            validateRequiredDateNotBefore(form.startDate, "data rozpoczęcia umowy", LocalDate.now().plusWeeks(1), "data dzisiejsza + 7 dni", "startDate")
    )
    validation.addValidation(
            validateElectiveDateNotBefore(form.endDate, "data zakończenia umowy", form.startDate, "data rozpoczęcia umowy", "endDate")
    )
    validation.addValidation(
            validateRequiredNumberBiggerThan(form.wageRateMin, "płaca minimalna", 1.0, "wageRateMin")
    )
    validation.addValidation(
            validateRequiredNumberBiggerThan(form.wageRateMax, "płaca maksymalna", form.wageRateMin?.toDouble(), "wageRateMax")
    )
    validation.addValidation(
            validateRequiredField(form.settlementType, "forma rozliczenia", "settlementType")
    )
    validation.addValidation(
            validateElectiveStringWithSize(form.description, "opis", 1, 255, "description")
    )
    return validation
}

fun validateComplexBeforeModificationNew(entity: CommissionEntity): Validation {
    val validation = Validation("")
    val activeRecordsAmount = entity.records
            .filter { it.status !in listOf(CommissionRecordStatus.REJECTED, CommissionRecordStatus.CANCELED) }
            .count()

    if (activeRecordsAmount >= 10) validation.addValidation(
            "Zamówienie może posiadać maksymalnie 10 rekordów",
            "",
            ValidationStatus.BLOCKER
    )

    return validation
}

fun validateComplexBeforeModificationExisted(form: CommissionRecordForm, entity: CommissionEntity): Validation {
    val validation = Validation("")
    val user = getCurrentUser()

    val record = entity.records.firstOrNull { form.id == it.id }
    if (record == null) validation.addValidation(
            "Brak rekordu zamówienia o wskazanym ID",
            "id",
            ValidationStatus.BLOCKER
    ) else {
        validation.addValidation(validateElectiveFieldFromCollection(
                form.status,
                "status",
                when (entity.status) {
                    CommissionStatus.CREATED -> when (record.status) {
                        CommissionRecordStatus.CREATED -> listOf(CommissionRecordStatus.CREATED, CommissionRecordStatus.CANCELED)
                        CommissionRecordStatus.CANCELED -> listOf(CommissionRecordStatus.CANCELED)
                        else -> listOf()
                    }
                    CommissionStatus.MODIFIED -> when (record.status) {
                        CommissionRecordStatus.CREATED -> listOf(CommissionRecordStatus.CREATED)
                        CommissionRecordStatus.ACCEPTED -> listOf(CommissionRecordStatus.MODIFIED, CommissionRecordStatus.CANCELED)
                        CommissionRecordStatus.MODIFIED -> listOf(CommissionRecordStatus.MODIFIED)
                        CommissionRecordStatus.REJECTED -> listOf(CommissionRecordStatus.MODIFIED, CommissionRecordStatus.CANCELED)
                        CommissionRecordStatus.CANCELED -> listOf(CommissionRecordStatus.CANCELED)
                        else -> listOf()
                    }
                    CommissionStatus.SENT, CommissionStatus.EXECUTION -> when (record.status) {
                        CommissionRecordStatus.CREATED -> listOf(CommissionRecordStatus.CREATED, CommissionRecordStatus.CANCELED)
                        CommissionRecordStatus.ACCEPTED -> listOf(CommissionRecordStatus.ACCEPTED, CommissionRecordStatus.CANCELED)
                        CommissionRecordStatus.MODIFIED -> listOf(CommissionRecordStatus.MODIFIED, CommissionRecordStatus.CANCELED)
                        else -> listOf(record.status)
                    }
                    else -> listOf()
                },
                "status"
        ))
        validation.addValidation(validateRequiredNumberInRange(
                form.ordered,
                "zamówiona ilość",
                record.acceptedOrdered?.toDouble() ?: 1.0,
                if (user.isAdmin()) 10000.0 else 200.0,
                "ordered"
        ))
    }

    return validation
}
