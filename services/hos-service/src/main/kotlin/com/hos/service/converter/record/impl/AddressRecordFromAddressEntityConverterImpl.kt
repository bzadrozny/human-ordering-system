package com.hos.service.converter.record.impl

import com.hos.service.converter.record.RecordConverter
import com.hos.service.model.entity.AddressEntity
import com.hos.service.model.record.AddressRecord
import org.springframework.stereotype.Component

@Component
class AddressRecordFromAddressEntityConverterImpl : RecordConverter<AddressEntity, AddressRecord>() {

    override fun create(source: AddressEntity): AddressRecord {
        return AddressRecord(
                id = source.id,
                street = source.street,
                number = source.number,
                city = source.city,
                postalCode = source.postalCode,
        )
    }

}