package com.hos.service.converter.entity.impl

import com.hos.service.converter.Converter
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.entity.CommissionRecordEntity
import com.hos.service.model.enum.CommissionDecision
import com.hos.service.model.enum.CommissionRecordStatus
import com.hos.service.model.enum.CommissionStatus
import com.hos.service.model.form.CommissionDecisionForm
import com.hos.service.model.form.CommissionRecordDecisionForm
import com.hos.service.repository.UserRepository
import org.springframework.stereotype.Component
import java.time.LocalDate
import javax.transaction.NotSupportedException

@Component
class CommissionEntityFromCommissionDecisionFormConverterImpl(
    private val userRepository: UserRepository,
    private val decisionRecordConverter: Converter<CommissionRecordDecisionForm, CommissionRecordEntity>
) : Converter<CommissionDecisionForm, CommissionEntity> {

    override fun create(source: CommissionDecisionForm): CommissionEntity {
        throw NotSupportedException("Decision can not be converted to new independent entity")
    }

    override fun merge(source: CommissionDecisionForm, target: CommissionEntity): CommissionEntity {

        target.status = when (source.decision) {
            CommissionDecision.ACCEPTED -> CommissionStatus.EXECUTION
            CommissionDecision.REJECTED -> CommissionStatus.REJECTED
            CommissionDecision.CANCELED -> CommissionStatus.CANCELED
            else -> target.status
        }
        target.decisionDate = LocalDate.now()
        target.realisationDate = source.realisationDate
        target.executor = source.executor?.let { userRepository.getOne(it) }
        target.decisionDescription = source.description

        if (source.decision == CommissionDecision.REJECTED) {
            target.records
                .filter { it.status !in listOf(CommissionRecordStatus.CANCELED) }
                .forEach {
                    decisionRecordConverter.merge(
                        source.records!!.first { rec -> rec.id == it.id },
                        it
                    )
                }
        } else {
            target.records
                .filter { it.status !in listOf(CommissionRecordStatus.CANCELED) }
                .forEach {
                    decisionRecordConverter.merge(
                        CommissionRecordDecisionForm(decision = source.decision),
                        it
                    )
                }
        }

        return target
    }

}