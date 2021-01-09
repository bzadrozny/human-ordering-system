package com.hos.service.usecase.uc003.impl

import com.hos.service.converter.Converter
import com.hos.service.model.common.Validation
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.enum.*
import com.hos.service.model.exception.NotAuthorizedException
import com.hos.service.model.exception.ResourceNotFoundException
import com.hos.service.model.exception.ValidationException
import com.hos.service.model.record.CommissionDetailsRecord
import com.hos.service.repository.CommissionRepository
import com.hos.service.usecase.uc003.DeleteCommission
import com.hos.service.utils.getCurrentUser
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
class DeleteCommissionImpl(
    private val commissionDetailsRecordConverter: Converter<CommissionEntity, CommissionDetailsRecord>,
    private val commissionRepository: CommissionRepository
) : DeleteCommission {

    @Transactional
    override fun deleteCommission(id: Long): CommissionDetailsRecord {
        val commission = commissionRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException(Resource.COMMISSION, QualifierType.ID, "$id")

        validateDeletion(commission)

        return if (commission.status == CommissionStatus.CREATED) {
            commissionRepository.delete(commission)
            commission.status = CommissionStatus.CANCELED
            commissionDetailsRecordConverter.create(commission)
        } else {
            commission.status = CommissionStatus.CANCELED
            commission.let { commissionRepository.save(it) }
                .let { commissionDetailsRecordConverter.create(it) }
        }
    }

    private fun validateDeletion(commission: CommissionEntity) {
        val user = getCurrentUser()
        val availableUser = user.isAdmin() || user.organisation == commission.location.organisation.id
        if (!availableUser) throw NotAuthorizedException(
            Resource.COMMISSION,
            QualifierType.ID,
            commission.id.toString()
        )

        val validation = Validation("commission")
        val availableStatus =
            commission.status in listOf(CommissionStatus.CREATED, CommissionStatus.MODIFIED, CommissionStatus.REJECTED)
        if (!availableStatus) {
            validation.addValidation(
                "Status zamówienia nie pozwala na jego usunięcie",
                "status",
                ValidationStatus.BLOCKER
            )
        }

        if (validation.hasBlocker()) throw ValidationException(validation)
    }

}