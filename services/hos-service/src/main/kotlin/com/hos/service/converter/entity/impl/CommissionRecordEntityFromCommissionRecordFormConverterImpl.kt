package com.hos.service.converter.entity.impl

import com.hos.service.converter.MultiConverter
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.entity.CommissionRecordEntity
import com.hos.service.model.form.CommissionRecordForm
import org.springframework.stereotype.Component

@Component
class CommissionRecordEntityFromCommissionRecordFormConverterImpl : MultiConverter<CommissionRecordForm, CommissionEntity, CommissionRecordEntity> {

    override fun create(source1: CommissionRecordForm, source2: CommissionEntity): CommissionRecordEntity {
        return CommissionRecordEntity(
                jobName = source1.jobName!!,
                ordered = source1.ordered!!,
                startDate = source1.startDate!!,
                endDate = source1.endDate,
                wageRateMin = source1.wageRateMin!!,
                wageRateMax = source1.wageRateMax!!,
                settlementType = source1.settlementType!!,
                description = source1.description,
                status = source1.status!!,
                commission = source2
        )
    }

    override fun merge(source1: CommissionRecordForm, source2: CommissionEntity, target: CommissionRecordEntity): CommissionRecordEntity {
        source1.jobName?.let { target.jobName = it }
        source1.ordered?.let { target.ordered = it }
        source1.startDate?.let { target.startDate = it }
        target.endDate = source1.endDate
        source1.wageRateMin?.let { target.wageRateMin = it }
        source1.wageRateMax?.let { target.wageRateMax = it }
        source1.settlementType?.let { target.settlementType = it }
        target.description = source1.description
        source1.status?.let { target.status = it }
        return target
    }

}