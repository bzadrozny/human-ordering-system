package com.hos.service.converter.record.impl

import com.hos.service.converter.record.RecordConverter
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.record.CommissionBasicRecord
import org.springframework.stereotype.Component

@Component
class CommissionBasicRecordFromCommissionEntityConverterImpl : RecordConverter<CommissionEntity, CommissionBasicRecord>() {

    override fun create(source: CommissionEntity): CommissionBasicRecord {
        val location = source.location
        val organisation = location.organisation
        return CommissionBasicRecord(
                id = source.id,
                orderDate = source.orderDate,
                status = source.status,
                organisationId = organisation.id,
                organisation = organisation.name,
                location = location.name,
                threaten = false
        )
    }

}