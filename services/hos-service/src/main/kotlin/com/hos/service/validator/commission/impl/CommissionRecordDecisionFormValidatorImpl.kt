package com.hos.service.validator.commission.impl

import com.hos.service.model.common.Validation
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.enum.CommissionDecision
import com.hos.service.model.enum.CommissionRecordStatus
import com.hos.service.model.enum.ValidationStatus
import com.hos.service.model.form.CommissionRecordDecisionForm
import com.hos.service.utils.*
import com.hos.service.validator.FormValidator
import org.springframework.stereotype.Component
import java.time.LocalDate
import javax.transaction.NotSupportedException

@Component
class CommissionRecordDecisionFormValidatorImpl : FormValidator<CommissionRecordDecisionForm, CommissionEntity> {

    override fun validateInitiallyBeforeRegistration(form: CommissionRecordDecisionForm): Validation {
        throw NotSupportedException("DecisionRecord can not be validated before creation as it is not connected to an independent entity")
    }

    override fun validateComplexBeforeRegistration(form: CommissionRecordDecisionForm): Validation {
        throw NotSupportedException("DecisionRecord can not be validated before creation as it is not connected to an independent entity")
    }

    override fun validateInitiallyBeforeModification(form: CommissionRecordDecisionForm): Validation {
        val validation = Validation("commissionRecordDecisionForm")

        validation.addValidation(validateRequiredField(form.id, "id", "id"))
        validation.addValidation(validateRequiredField(form.decision, "decyzja", "decision"))

        when (form.decision) {
            CommissionDecision.REJECTED -> {
                validation.addValidation(
                    validateRequiredField(
                        form.accepted,
                        "zaakceptowana ilość",
                        "accepted"
                    )
                )
                validation.addValidation(
                    validateRequiredField(
                        form.startDate,
                        "zaakceptowana data rozpoczęcia umowy",
                        "startDate"
                    )
                )
            }
            CommissionDecision.ACCEPTED,
            CommissionDecision.CANCELED -> {
                validation.addValidation(
                    validateForbiddenField(
                        form.accepted,
                        "zaakceptowana ilość",
                        "accepted"
                    )
                )
                validation.addValidation(
                    validateForbiddenField(
                        form.startDate,
                        "zaakceptowana data rozpoczęcia umowy",
                        "startDate"
                    )
                )
            }
        }
        return validation
    }

    override fun validateComplexBeforeModification(
        form: CommissionRecordDecisionForm,
        entity: CommissionEntity
    ): Validation {
        val validation = Validation("commissionRecordDecisionForm")

        val record = entity.records.firstOrNull { it.id == form.id }
        if (record == null) {
            validation.addValidation(
                "Brak redordu dla decyzji o id: '${record?.id}'",
                "records.id",
                ValidationStatus.BLOCKER
            )
            return validation
        }

        if (record.status !in listOf(CommissionRecordStatus.CREATED, CommissionRecordStatus.MODIFIED)) {
            validation.addValidation(
                "Brak możliwości wystawienia decyzji dla zablokowanego rekordu o id: '${record.id}'",
                "records.id",
                ValidationStatus.BLOCKER
            )
            return validation
        }

        if (form.decision == CommissionDecision.REJECTED) {
            validation.addValidation(
                validateRequiredNumberInRange(
                    form.accepted,
                    "zaakceptowana ilość",
                    1,
                    record.ordered,
                    "accepted"
                )
            )
            validation.addValidation(
                validateRequiredDateNotBefore(
                    form.startDate,
                    "zaakceptowana data rozpoczęcia umowy",
                    record.startDate,
                    "zamówiona data rozpoczęcia pracy",
                    "startDate"
                )
            )
        }

        return validation
    }

}