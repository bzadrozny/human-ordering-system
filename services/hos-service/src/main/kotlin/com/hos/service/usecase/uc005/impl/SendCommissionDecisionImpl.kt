package com.hos.service.usecase.uc005.impl

import com.hos.service.converter.Converter
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.enum.QualifierType
import com.hos.service.model.enum.Resource
import com.hos.service.model.exception.ResourceNotFoundException
import com.hos.service.model.exception.ValidationException
import com.hos.service.model.form.CommissionDecisionForm
import com.hos.service.model.record.CommissionDetailsRecord
import com.hos.service.repository.CommissionRepository
import com.hos.service.usecase.uc005.SendCommissionDecision
import com.hos.service.validator.FormValidator
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class SendCommissionDecisionImpl(
        private val decisionValidator: FormValidator<CommissionDecisionForm, CommissionEntity>,
        private val decisionConverter: Converter<CommissionDecisionForm, CommissionEntity>,
        private val commissionDetailsRecordConverter: Converter<CommissionEntity, CommissionDetailsRecord>,
        private val commissionRepository: CommissionRepository
) : SendCommissionDecision {

    override fun sendCommissionDecision(form: CommissionDecisionForm): CommissionDetailsRecord {
        decisionValidator.validateInitiallyBeforeRegistration(form).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }

        val commission = commissionRepository.findByIdOrNull(form.id!!)
                ?: throw ResourceNotFoundException(Resource.COMMISSION, QualifierType.ID, "${form.id}")

        decisionValidator.validateComplexBeforeModification(form, commission).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }

        decisionConverter.merge(form, commission)
        //TODO: powiadomienie mailowe

        return commission
                .let { commissionRepository.save(it) }
                .let { commissionDetailsRecordConverter.create(it) }
    }

}