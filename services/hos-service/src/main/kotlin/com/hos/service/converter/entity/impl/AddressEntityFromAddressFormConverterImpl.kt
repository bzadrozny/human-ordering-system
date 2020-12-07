package com.hos.service.converter.entity.impl

import com.hos.service.converter.Converter
import com.hos.service.model.entity.AddressEntity
import com.hos.service.model.form.AddressForm
import org.springframework.stereotype.Component

@Component
class AddressEntityFromAddressFormConverterImpl : Converter<AddressForm, AddressEntity> {

    override fun create(source: AddressForm): AddressEntity {
        return AddressEntity(
                street = source.street!!,
                number = source.number!!,
                city = source.city!!,
                postalCode = source.postalCode!!
        )
    }

    override fun merge(source: AddressForm, target: AddressEntity): AddressEntity {
        source.street?.let { target.street = it }
        source.number?.let { target.number = it }
        source.city?.let { target.city = it }
        source.postalCode?.let { target.postalCode = it }
        return target
    }
}