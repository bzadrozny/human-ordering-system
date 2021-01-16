package com.hos.service.converter.entity.impl

import com.hos.service.converter.Converter
import com.hos.service.converter.MultiConverter
import com.hos.service.model.entity.AddressEntity
import com.hos.service.model.entity.LocationEntity
import com.hos.service.model.entity.OrganisationEntity
import com.hos.service.model.form.AddressForm
import com.hos.service.model.form.LocationForm
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
@Qualifier("LocationConverterImpl")
class LocationEntityFromLocationFormConverterImpl(
        private val addressConverter: Converter<AddressForm, AddressEntity>
) : MultiConverter<LocationForm, OrganisationEntity, LocationEntity> {

    override fun create(source1: LocationForm, source2: OrganisationEntity): LocationEntity {
        return LocationEntity(
                name = source1.name!!,
                registeredOffice = source1.registeredOffice!!,
                address = addressConverter.create(source1.address!!),
                status = source1.status!!,
                organisation = source2
        )
    }

    override fun merge(source1: LocationForm, source2: OrganisationEntity, target: LocationEntity): LocationEntity {
        source1.name?.let { target.name = it }
        source1.registeredOffice?.let { target.registeredOffice = it }
        source1.address?.let { addressConverter.merge(it, target.address) }
        source1.status?.let { target.status = it }
        return target
    }

}