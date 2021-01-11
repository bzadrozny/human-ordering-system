package com.hos.service.validator.commission.impl

import com.hos.service.model.common.Validation
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.enum.CommissionDecision
import com.hos.service.model.enum.CommissionRecordStatus
import com.hos.service.model.enum.CommissionStatus
import com.hos.service.model.enum.ValidationStatus
import com.hos.service.model.form.CommissionDecisionForm
import com.hos.service.model.form.CommissionRecordDecisionForm
import com.hos.service.repository.UserRepository
import com.hos.service.utils.*
import com.hos.service.validator.FormValidator
import org.springframework.stereotype.Component
import java.time.LocalDate
import javax.transaction.NotSupportedException

@Component
class CommissionDecisionFormValidatorImpl(
    private val userRepository: UserRepository,
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
        validation.addValidation(validateRequiredField(form.decision, "decyzja", "decision"))
        validation.addValidation(validateRequiredField(form.executor, "opiekun", "executor"))

        when (form.decision) {
            CommissionDecision.ACCEPTED -> {
                validation.addValidation(
                    validateRequiredField(form.realisationDate, "data realizacji", "realisationDate")
                )
                validation.addValidation(
                    validateForbiddenField(form.records, "stanowiska", "records")
                )
                validation.addValidation(
                    validateElectiveStringWithSize(form.description, "opis", 5, 250, "description")
                )
            }
            CommissionDecision.REJECTED -> {
                validation.addValidation(
                    validateForbiddenField(form.realisationDate, "data realizacji", "realisationDate")
                )
                validation.addValidation(
                    validateRequiredField(form.records, "stanowiska", "records")
                )
                form.records?.forEach {
                    validation.addValidation(decisionRecordValidator.validateInitiallyBeforeModification(it))
                }
                validation.addValidation(
                    validateElectiveStringWithSize(form.description, "opis", 5, 250, "description")
                )
            }
            CommissionDecision.CANCELED -> {
                validation.addValidation(
                    validateForbiddenField(form.realisationDate, "data realizacji", "realisationDate")
                )
                validation.addValidation(
                    validateForbiddenField(form.records, "stanowiska", "records")
                )
                validation.addValidation(
                    validateRequiredStringWithSize(form.description, "opis", 5, 250, "description")
                )
            }
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

        val executor = userRepository.findById(form.executor!!).orElse(null)
        if (executor == null) {
            validation.addValidation(
                "Brak opiekuna o wskazanym ID",
                "executor",
                ValidationStatus.BLOCKER
            )
        } else if (!executor.isManager()) {
            validation.addValidation(
                "Wybrany opiekun musi posiadać uprawnienia managerskie",
                "executor",
                ValidationStatus.BLOCKER
            )
        }

        if (form.decision == CommissionDecision.ACCEPTED) {
            val realisationDateBeforeNow = form.realisationDate?.isBefore(LocalDate.now()) ?: false
            val anyRecBeforeRealisationDate = entity.records
                .map { it.startDate }
                .any { it.isBefore(form.realisationDate) }
            if (realisationDateBeforeNow || anyRecBeforeRealisationDate) {
                validation.addValidation(
                    "Data realizacji nie może być wcześniejsza niż data dzisiejsza i późniejsza niż zaakceptowane daty zamówionych stanowisk",
                    "realisationDate",
                    ValidationStatus.BLOCKER
                )
            }
        }

        if (form.decision == CommissionDecision.REJECTED) {
            entity.records
                .filter { it.status !in listOf(CommissionRecordStatus.REJECTED, CommissionRecordStatus.CANCELED) }
                .forEach { record ->
                    form.records?.first { record.id == it.id } ?: validation.addValidation(
                        "Brak decyzji dla stanowiska o id: '${record.id}'",
                        "records.id",
                        ValidationStatus.BLOCKER
                    )
                }
            form.records?.forEach {
                validation.addValidation(decisionRecordValidator.validateComplexBeforeModification(it, entity))
            }
        }

        return validation
    }

}