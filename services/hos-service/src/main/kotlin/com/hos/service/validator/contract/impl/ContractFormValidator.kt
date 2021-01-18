package com.hos.service.validator.contract.impl

import com.hos.service.model.common.Validation
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.enum.*
import com.hos.service.model.form.ContractForm
import com.hos.service.repository.CandidateRepository
import com.hos.service.repository.UserRepository
import com.hos.service.utils.*
import com.hos.service.utils.validateRequiredDateNotBefore
import com.hos.service.validator.FormValidator
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.LocalDate
import javax.transaction.NotSupportedException

@Component
class ContractFormValidator(
    private val userRepository: UserRepository,
    private val candidateRepository: CandidateRepository
) : FormValidator<ContractForm, CommissionEntity> {

    override fun validateInitiallyBeforeRegistration(form: ContractForm): Validation {
        val validation = Validation("contractForm")

        validation.addValidation(validateForbiddenField(form.id, "id", "id"))
        validation.addValidation(validateInitiallyCommon(form))

        return validation
    }

    override fun validateComplexBeforeRegistration(form: ContractForm): Validation {
        throw NotSupportedException("Contract has to be validated by method: 'validateComplexBeforeModification'")
    }

    override fun validateInitiallyBeforeModification(form: ContractForm): Validation {
        val validation = Validation("contractForm")

        validation.addValidation(validateRequiredField(form.id, "id", "id"))
        validation.addValidation(validateInitiallyCommon(form))

        return validation
    }

    override fun validateComplexBeforeModification(form: ContractForm, entity: CommissionEntity): Validation {
        val validation = Validation("contractForm")

        if (entity.status != CommissionStatus.EXECUTION) {
            validation.addValidation(
                "Brak możliwości dodania kontraktu do zamówienia nie będącego w stanie obsługi",
                "status",
                ValidationStatus.BLOCKER
            )
            return validation
        }

        val record = entity.records.firstOrNull { it.id == form.commissionRecord }
        if (record != null) {
            if (record.status != CommissionRecordStatus.ACCEPTED) validation.addValidation(
                "Brak możliwości dodania kontraktu do rekordu zamówienia nie będącego w stanie obsługi",
                "status",
                ValidationStatus.BLOCKER
            )
            else if (form.id == null) {
                if (record.contracts.size >= record.acceptedOrdered!!) validation.addValidation(
                    "Brak możliwości dodania kolejnego kontraktu, zamówienie zrealizowane",
                    "contracts",
                    ValidationStatus.BLOCKER
                )
            } else {
                val contract = record.contracts.firstOrNull { it.id == form.id }
                if (contract != null && contract.approved == true) validation.addValidation(
                    "Brak możliwości edycji zatwierdzonego przez klienta kontraktu",
                    "contracts",
                    ValidationStatus.BLOCKER
                )
            }
            if (validation.hasBlocker()) return validation

            val user = getCurrentUser()
            if (user.isAdmin()) {
                val recuiter = userRepository.findByIdOrNull(form.recruiter)
                if (recuiter == null || !recuiter.isRecruiter()) {
                    validation.addValidation(
                        "Jako autor kontraktu może być wybrany jedynie rekruter",
                        "recruiter",
                        ValidationStatus.BLOCKER
                    )
                }
            } else if (user.id != form.recruiter) {
                validation.addValidation(
                    "Rekruter jako autora kontraktu może przypisać jedynie siebie",
                    "recruiter",
                    ValidationStatus.BLOCKER
                )
            }

            form.candidate?.let {
                if (!candidateRepository.existsById(it)) validation.addValidation(
                    "Brak kandydata o id: $it",
                    "candidate",
                    ValidationStatus.BLOCKER
                )
            }
            validation.addValidation(
                validateRequiredDateNotBefore(
                    form.startDate,
                    "data rozpoczęcia pracy",
                    record.startDate,
                    "data umowy",
                    "startDate"
                )
            )
            validation.addValidation(
                validateElectiveDateNotBefore(
                    form.endDate,
                    "data zakończenia pracy",
                    record.startDate,
                    "zamówiona data rozpoczęcia pracy",
                    "endDate"
                )
            )
            validation.addValidation(
                validateElectiveDateNotAfter(
                    form.endDate,
                    "data zakończenia pracy",
                    record.endDate,
                    "zamówiona data zakończenia pracy",
                    "endDate"
                )
            )
            validation.addValidation(
                validateRequiredNumberInRange(
                    form.salary,
                    "stawka",
                    record.wageRateMin.toDouble(),
                    record.wageRateMax.toDouble(),
                    "commission"
                )
            )
        }

        return validation
    }

}

private fun validateInitiallyCommon(form: ContractForm): Validation {
    val validation = Validation("")

    validation.addValidation(
        validateRequiredField(form.commission, "id zamówienia", "commission")
    )
    validation.addValidation(
        validateRequiredField(form.commissionRecord, "id rekordu zamówienia", "commissionRecord")
    )
    validation.addValidation(
        validateRequiredStringWithSize(form.code, "code", 5, 25, "code")
    )
    validation.addValidation(
        validateRequiredDateNotAfter(
            form.contractDate,
            "data umowy",
            LocalDate.now(),
            "data dzisiejsza",
            "contractDate"
        )
    )
    validation.addValidation(
        validateRequiredDateNotBefore(
            form.startDate,
            "data rozpoczęcia pracy",
            form.contractDate,
            "data umowy",
            "startDate"
        )
    )
    validation.addValidation(
        validateElectiveDateNotBefore(
            form.endDate,
            "data zakończenia pracy",
            form.startDate,
            "data rozpoczęcia pracy",
            "startDate"
        )
    )
    validation.addValidation(
        validateRequiredNumberBiggerThan(form.salary, "stawka", 1.0, "commission")
    )
    validation.addValidation(
        validateRequiredField(form.contractType, "forma rozliczenia", "contractType")
    )
    validation.addValidation(
        validateElectiveStringWithSize(form.description, "opis", 0, 255, "description")
    )

    return validation
}
