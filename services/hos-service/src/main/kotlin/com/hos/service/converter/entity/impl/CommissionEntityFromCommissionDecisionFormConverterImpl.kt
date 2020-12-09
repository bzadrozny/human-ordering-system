package com.hos.service.converter.entity.impl

import com.hos.service.converter.Converter
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.enum.CommissionDecision
import com.hos.service.model.enum.CommissionStatus
import com.hos.service.model.form.CommissionDecisionForm
import org.springframework.stereotype.Component
import java.time.LocalDate
import javax.transaction.NotSupportedException

@Component
class CommissionEntityFromCommissionDecisionFormConverterImpl : Converter<CommissionDecisionForm, CommissionEntity> {

    override fun create(source: CommissionDecisionForm): CommissionEntity {
        throw NotSupportedException("Decision can not be converted to new independent entity")
    }

    override fun merge(source: CommissionDecisionForm, target: CommissionEntity): CommissionEntity {
        target.status = when (source.decision) {
            CommissionDecision.ACCEPTED -> CommissionStatus.EXECUTION
            CommissionDecision.REJECTED -> CommissionStatus.REJECTED
            else -> target.status
        }
        target.decisionDescription = source.description
        target.decisionDate = LocalDate.now()

        return target
    }

}