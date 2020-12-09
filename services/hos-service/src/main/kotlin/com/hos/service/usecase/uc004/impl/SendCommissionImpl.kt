package com.hos.service.usecase.uc004.impl

import com.hos.service.converter.Converter
import com.hos.service.model.common.Validation
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.enum.*
import com.hos.service.model.exception.NotAuthorizedException
import com.hos.service.model.exception.ResourceNotFoundException
import com.hos.service.model.exception.ValidationException
import com.hos.service.model.record.CommissionDetailsRecord
import com.hos.service.repository.CommissionRepository
import com.hos.service.usecase.uc004.SendCommission
import com.hos.service.utils.getCurrentUser
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class SendCommissionImpl(
        private val commissionDetailsRecordConverter: Converter<CommissionEntity, CommissionDetailsRecord>,
        private val commissionRepository: CommissionRepository
) : SendCommission {

    override fun sendCommission(id: Long): CommissionDetailsRecord {
        val commission = commissionRepository.findByIdOrNull(id)
                ?: throw ResourceNotFoundException(Resource.COMMISSION, QualifierType.ID, "$id")

        validateSending(commission)
        commission.status = CommissionStatus.SENT
        commission.orderDate = LocalDate.now()

        //TODO: powiadomienie mailowe

        return commission
                .let { commissionRepository.save(it) }
                .let { commissionDetailsRecordConverter.create(it) }
    }

    private fun validateSending(commission: CommissionEntity) {
        val user = getCurrentUser()
        if (user.organisation != commission.location.organisation.id)
            throw NotAuthorizedException(Resource.COMMISSION, QualifierType.ID, commission.id.toString())

        val validation = Validation("commission")
        val availableStatus = commission.status in listOf(CommissionStatus.CREATED, CommissionStatus.MODIFIED)
        if (!availableStatus) {
            validation.addValidation(
                    "Status zamówienia nie pozwala na jego wysłanie",
                    "status",
                    ValidationStatus.BLOCKER
            )
        }

        commission.records
                .firstOrNull { it.status == CommissionRecordStatus.REJECTED }
                ?.let {
                    validation.addValidation(
                            "Nie można wysłać zamówienia z zastrzeżonym rekordem",
                            "status",
                            ValidationStatus.BLOCKER
                    )
                }

        commission.records
                .firstOrNull { it.status in listOf(CommissionRecordStatus.CREATED, CommissionRecordStatus.MODIFIED) }
                ?: let {
                    validation.addValidation(
                            "Nie można wysłać zamówienia z ani jednym aktywnym rekordem",
                            "status",
                            ValidationStatus.BLOCKER
                    )
                }

        if (validation.hasBlocker()) throw ValidationException(validation)
    }

}