package com.hos.service.converter.entity.impl

import com.hos.service.converter.Converter
import com.hos.service.model.entity.CommissionRecordEntity
import com.hos.service.model.enum.CommissionDecision
import com.hos.service.model.enum.CommissionRecordStatus
import com.hos.service.model.form.CommissionRecordDecisionForm
import org.springframework.stereotype.Component
import javax.transaction.NotSupportedException

@Component
class CommissionRecordEntityFromCommissionRecordDecisionFormConverterImpl :
    Converter<CommissionRecordDecisionForm, CommissionRecordEntity> {

    override fun create(source: CommissionRecordDecisionForm): CommissionRecordEntity {
        throw NotSupportedException("Decision can not be converted to new independent entity")
    }

    override fun merge(source: CommissionRecordDecisionForm, target: CommissionRecordEntity): CommissionRecordEntity {
        when (source.decision) {
            CommissionDecision.ACCEPTED -> {
                target.status = CommissionRecordStatus.ACCEPTED
                target.acceptedOrdered = target.ordered
                target.acceptedStartDate = target.startDate
            }
            CommissionDecision.REJECTED -> {
                target.status = CommissionRecordStatus.REJECTED
                target.acceptedOrdered = source.accepted
                target.acceptedStartDate = source.startDate
            }
            CommissionDecision.CANCELED -> {
                target.status = CommissionRecordStatus.CANCELED
                target.acceptedOrdered = null
                target.acceptedStartDate = null
            }
        }
        return target
    }

}