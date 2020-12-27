package com.hos.service.converter.record.impl

import com.hos.service.converter.Converter
import com.hos.service.converter.record.RecordConverter
import com.hos.service.model.entity.AddressEntity
import com.hos.service.model.entity.LocationEntity
import com.hos.service.model.record.AddressRecord
import com.hos.service.model.record.LocationRecord
import org.springframework.stereotype.Component

@Component
class LocationRecordFromLocationEntityConverterImpl(
        private val addressConverter: Converter<AddressEntity, AddressRecord>
) : RecordConverter<LocationEntity, LocationRecord>() {

    override fun create(source: LocationEntity): LocationRecord {
        return LocationRecord(
                id = source.id,
                name = source.name,
                registeredOffice = source.registeredOffice,
                address = addressConverter.create(source.address),
                organisation = source.organisation.id,
                status = source.status
        )
    }

}