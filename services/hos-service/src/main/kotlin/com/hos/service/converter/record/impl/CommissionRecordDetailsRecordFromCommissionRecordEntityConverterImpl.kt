package com.hos.service.converter.record.impl

import com.hos.service.converter.Converter
import com.hos.service.converter.record.RecordConverter
import com.hos.service.model.entity.CommissionRecordEntity
import com.hos.service.model.entity.ContractEntity
import com.hos.service.model.record.CommissionRecordDetailsRecord
import com.hos.service.model.record.ContractBasicRecord
import com.hos.service.model.record.ContractDetailsRecord
import org.springframework.stereotype.Component

@Component
class CommissionRecordDetailsRecordFromCommissionRecordEntityConverterImpl(
        private val contractConverter: Converter<ContractEntity, ContractDetailsRecord>
) : RecordConverter<CommissionRecordEntity, CommissionRecordDetailsRecord>() {

    override fun create(source: CommissionRecordEntity): CommissionRecordDetailsRecord {
        return CommissionRecordDetailsRecord(
                id = source.id,
                status = source.status,
                jobName = source.jobName,
                ordered = source.ordered,
                acceptedOrdered = source.acceptedOrdered,
                recruited = source.contracts.size,
                startDate = source.startDate,
                acceptedStartDate = source.acceptedStartDate,
                endDate =  source.endDate,
                wageRateMin = source.wageRateMin,
                wageRateMax = source.wageRateMax,
                settlementType = source.settlementType,
                contracts = source.contracts.map(contractConverter::create),
                description = source.description,
        )
    }

}