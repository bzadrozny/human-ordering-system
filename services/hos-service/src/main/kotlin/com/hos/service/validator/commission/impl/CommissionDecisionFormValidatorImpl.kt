package com.hos.service.validator.commission.impl

import com.hos.service.model.common.Validation
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.enum.CommissionDecision
import com.hos.service.model.enum.CommissionRecordStatus
import com.hos.service.model.enum.CommissionStatus
import com.hos.service.model.enum.ValidationStatus
import com.hos.service.model.form.CommissionDecisionForm
import com.hos.service.model.form.CommissionRecordDecisionForm
import com.hos.service.utils.*
import com.hos.service.validator.FormValidator
import org.springframework.stereotype.Component
import javax.transaction.NotSupportedException

@Component
class CommissionDecisionFormValidatorImpl(
        private val decisionRecordValidator: FormValidator<CommissionRecordDecisionForm, CommissionEntity>
) : FormValidator<CommissionDecisionForm, CommissionEntity> {

    override fun validateInitiallyBeforeRegistration(form: CommissionDecisionForm): Validation {
        throw NotSupportedException("Decision can not be validated before creation as it is not connected to an independent entity")
    }

    override fun validateComplexBeforeRegistration(form: CommissionDecisionForm): Validation {
        throw NotSupportedException("Decision can not be validated before creation as it is not connected to an independent entity")
    }

    override fun validateInitiallyBeforeModification(form: CommissionDecisionForm): Validation {
        val validation = Validation("commissionDecisionForm")

        validation.addValidation(validateRequiredField(form.id, "id", "id"))
        validation.addValidation(validateElectiveStringWithSize(form.description, "opis", 0, 250, "description"))
        validation.addValidation(validateRequiredFieldFromCollection(
                form.decision,
                "decyzja",
                listOf(CommissionDecision.ACCEPTED, CommissionDecision.REJECTED),
                "decision"
        ))
        validation.addValidation(validateRequiredCollectionWithMinSize(form.records, "rekordy", 1, "records"))
        form.records?.forEach {
            validation.addValidation(decisionRecordValidator.validateInitiallyBeforeModification(it))
        }

        return validation
    }

    override fun validateComplexBeforeModification(form: CommissionDecisionForm, entity: CommissionEntity): Validation {
        val validation = Validation("commissionDecisionForm")

        if (entity.status != CommissionStatus.SENT) {
            validation.addValidation(
                    "Brak możliwości rejestracji decyzji dla nie wysłanego zamówienia",
                    "status",
                    ValidationStatus.BLOCKER
            )
        }

        entity.records
                .filter { it.status in listOf(CommissionRecordStatus.CREATED, CommissionRecordStatus.MODIFIED) }
                .forEach { record ->
                    form.records?.first { record.id == it.id } ?: validation.addValidation(
                            "Brak decyzji dla rekordu o id: '${record.id}'",
                            "records.id",
                            ValidationStatus.BLOCKER
                    )
                }

        form.records?.forEach {
            validation.addValidation(decisionRecordValidator.validateComplexBeforeModification(it, entity))
        }

        return validation
    }

}