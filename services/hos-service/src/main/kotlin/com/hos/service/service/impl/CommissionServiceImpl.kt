package com.hos.service.service.impl

import com.hos.service.converter.Converter
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.enum.QualifierType
import com.hos.service.model.enum.Resource
import com.hos.service.model.exception.ResourceNotFoundException
import com.hos.service.model.exception.ValidationException
import com.hos.service.model.form.CommissionDecisionForm
import com.hos.service.model.form.CommissionFilterForm
import com.hos.service.model.form.CommissionForm
import com.hos.service.model.record.CommissionBasicRecord
import com.hos.service.model.record.CommissionDetailsRecord
import com.hos.service.repository.CommissionRepository
import com.hos.service.service.CommissionService
import com.hos.service.usecase.uc002.FindFilteredCommissions
import com.hos.service.usecase.uc003.DeleteCommission
import com.hos.service.usecase.uc004.SendCommission
import com.hos.service.usecase.uc005.SendCommissionDecision
import com.hos.service.usecase.uc006.CompleteCommission
import com.hos.service.validator.FormValidator
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommissionServiceImpl(
        private val uc002: FindFilteredCommissions,
        private val uc003: DeleteCommission,
        private val uc004: SendCommission,
        private val uc005: SendCommissionDecision,
        private val uc006: CompleteCommission,
        private val commissionValidator: FormValidator<CommissionForm, CommissionEntity>,
        private val commissionConverter: Converter<CommissionForm, CommissionEntity>,
        private val commissionBasicRecordConverter: Converter<CommissionEntity, CommissionBasicRecord>,
        private val commissionDetailsRecordConverter: Converter<CommissionEntity, CommissionDetailsRecord>,
        private val commissionRepository: CommissionRepository
) : CommissionService {

    override fun findAllCommissions(filter: CommissionFilterForm): List<CommissionBasicRecord> {
        return uc002.findFilteredCommissions(filter)
                .map { commissionBasicRecordConverter.create(it) }
    }

    override fun findCommissionDetailsById(id: Long): CommissionDetailsRecord {
        return commissionRepository.findByIdOrNull(id)
                ?.let { commissionDetailsRecordConverter.create(it) }
                ?: throw ResourceNotFoundException(Resource.COMMISSION, QualifierType.ID, "$id")
    }

    override fun createCommission(form: CommissionForm): CommissionDetailsRecord {
        commissionValidator.validateInitiallyBeforeRegistration(form).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }
        commissionValidator.validateComplexBeforeRegistration(form).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }
        return commissionConverter.create(form)
                .let { commissionRepository.save(it) }
                .let { commissionDetailsRecordConverter.create(it) }
    }

    override fun modifyCommission(form: CommissionForm): CommissionDetailsRecord {
        commissionValidator.validateInitiallyBeforeModification(form).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }
        val commission = commissionRepository.findById(form.id!!).orElseThrow {
            ResourceNotFoundException(
                    Resource.COMMISSION,
                    QualifierType.ID,
                    "${form.id}"
            )
        }
        commissionValidator.validateComplexBeforeModification(form, commission).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }

        val savedCommission = commissionConverter.merge(form, commission)
                .let { commissionRepository.save(it) }

        //TODO: w przypadku edycji w statusie

        return savedCommission
                .let { commissionDetailsRecordConverter.create(it) }
    }

    override fun deleteCommission(id: Long): CommissionDetailsRecord {
        return uc003.deleteCommission(id)
    }

    override fun sendCommission(id: Long): CommissionDetailsRecord {
        return uc004.sendCommission(id)
    }

    override fun sendCommissionDecision(form: CommissionDecisionForm): CommissionDetailsRecord {
        return uc005.sendCommissionDecision(form)
    }

    override fun completeCommission(id: Long): CommissionDetailsRecord {
        return uc006.completeCommission(id)
    }

}