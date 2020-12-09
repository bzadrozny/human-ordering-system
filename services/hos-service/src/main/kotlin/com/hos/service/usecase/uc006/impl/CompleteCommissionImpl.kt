package com.hos.service.usecase.uc006.impl

import com.hos.service.converter.Converter
import com.hos.service.model.common.Validation
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.enum.CommissionStatus
import com.hos.service.model.enum.QualifierType
import com.hos.service.model.enum.Resource
import com.hos.service.model.enum.ValidationStatus
import com.hos.service.model.exception.ResourceNotFoundException
import com.hos.service.model.exception.ValidationException
import com.hos.service.model.record.CommissionDetailsRecord
import com.hos.service.repository.CommissionRepository
import com.hos.service.usecase.uc006.CompleteCommission
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class CompleteCommissionImpl(
        private val commissionDetailsRecordConverter: Converter<CommissionEntity, CommissionDetailsRecord>,
        private val commissionRepository: CommissionRepository
) : CompleteCommission {

    override fun completeCommission(id: Long): CommissionDetailsRecord {
        val commission = commissionRepository.findByIdOrNull(id)
                ?: throw ResourceNotFoundException(Resource.COMMISSION, QualifierType.ID, "$id")

        validateCompletion(commission)
        commission.status = CommissionStatus.COMPLETED
        commission.completedDate = LocalDate.now()

        //TODO: powiadomienie mailowe

        return commission
                .let { commissionRepository.save(it) }
                .let { commissionDetailsRecordConverter.create(it) }
    }

    private fun validateCompletion(commission: CommissionEntity) {
        val validation = Validation("commission")

        if (commission.status != CommissionStatus.EXECUTION) {
            validation.addValidation(
                    "Można zakończyć jedynie procesowane zamówienie",
                    "status",
                    ValidationStatus.BLOCKER
            )
            throw ValidationException(validation)
        }

        if (commission.status != CommissionStatus.EXECUTION) {
            validation.addValidation(
                    "Można zakończyć jedynie procesowane zamówienie",
                    "status",
                    ValidationStatus.BLOCKER
            )
            throw ValidationException(validation)
        }

        if (commission.records.any { it.contracts.size < it.acceptedOrdered!! }) {
            validation.addValidation(
                    "Nie można zakończyć zamówienia przed jego pełną realizacją",
                    "acceptedOrdered",
                    ValidationStatus.BLOCKER
            )
            throw ValidationException(validation)
        }

        commission.records.forEach { record ->
            record.contracts
                    .firstOrNull { it.approved == true }
                    ?.let {
                        validation.addValidation(
                                "W rekordzie: ${record.id} są niezatwierdzone kontrakty: ${it.code}",
                                "records.approved",
                                ValidationStatus.BLOCKER
                        )
                    }
        }

        if (validation.hasBlocker()) throw ValidationException(validation)
    }

}