package com.hos.service.converter.entity.impl

import com.hos.service.converter.Converter
import com.hos.service.model.entity.CommissionRecordEntity
import com.hos.service.model.enum.CommissionDecision
import com.hos.service.model.enum.CommissionRecordStatus
import com.hos.service.model.form.CommissionRecordDecisionForm
import org.springframework.stereotype.Component
import javax.transaction.NotSupportedException

@Component
class CommissionRecordEntityFromCommissionRecordDecisionFormConverterImpl : Converter<CommissionRecordDecisionForm, CommissionRecordEntity> {

    override fun create(source: CommissionRecordDecisionForm): CommissionRecordEntity {
        throw NotSupportedException("Decision can not be converted to new independent entity")
    }

    override fun merge(source: CommissionRecordDecisionForm, target: CommissionRecordEntity): CommissionRecordEntity {
        target.status = when (source.decision) {
            CommissionDecision.ACCEPTED -> CommissionRecordStatus.ACCEPTED
            CommissionDecision.MODIFIED -> CommissionRecordStatus.MODIFIED
            CommissionDecision.REJECTED -> CommissionRecordStatus.REJECTED
            else -> target.status
        }
        target.acceptedOrdered = source.accepted ?: target.ordered
        target.acceptedStartDate = source.startDate ?: target.startDate
        return target
    }

}