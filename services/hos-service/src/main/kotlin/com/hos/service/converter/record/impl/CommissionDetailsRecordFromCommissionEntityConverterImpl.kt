package com.hos.service.converter.record.impl

import com.hos.service.converter.Converter
import com.hos.service.converter.record.RecordConverter
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.entity.CommissionRecordEntity
import com.hos.service.model.entity.LocationEntity
import com.hos.service.model.entity.UserEntity
import com.hos.service.model.record.*
import org.springframework.stereotype.Component

@Component
class CommissionDetailsRecordFromCommissionEntityConverterImpl(
    private val userConverter: Converter<UserEntity, UserBasicRecord>,
    private val locationConverter: Converter<LocationEntity, LocationRecord>,
    private val recordConverter: Converter<CommissionRecordEntity, CommissionRecordDetailsRecord>
) : RecordConverter<CommissionEntity, CommissionDetailsRecord>() {

    override fun create(source: CommissionEntity): CommissionDetailsRecord {
        return CommissionDetailsRecord(
            id = source.id,
            status = source.status,
            orderDate = source.orderDate,
            decisionDate = source.decisionDate,
            decisionDescription = source.decisionDescription,
            realisationDate = source.realisationDate,
            completedDate = source.completedDate,
            principal = userConverter.create(source.principal),
            location = locationConverter.create(source.location),
            executor = source.executor?.let { userConverter.create(it) },
            records = source.records.map(recordConverter::create),
            description = source.description
        )
    }

}