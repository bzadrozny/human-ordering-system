package com.hos.service.converter.record.impl

import com.hos.service.converter.record.RecordConverter
import com.hos.service.model.entity.ContractEntity
import com.hos.service.model.record.ContractBasicRecord
import org.springframework.stereotype.Component

@Component
class ContractBasicRecordFromContractEntityConverterImpl : RecordConverter<ContractEntity, ContractBasicRecord>() {

    override fun create(source: ContractEntity): ContractBasicRecord {
        return ContractBasicRecord(
                id = source.id,
                code = source.code,
                startDate = source.startDate
        )
    }

}