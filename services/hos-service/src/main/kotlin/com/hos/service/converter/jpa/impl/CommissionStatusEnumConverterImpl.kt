package com.hos.service.converter.jpa.impl

import com.hos.service.model.enum.CommissionStatus
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class CommissionStatusEnumConverterImpl : AttributeConverter<CommissionStatus, Int> {

    override fun convertToDatabaseColumn(attribute: CommissionStatus?): Int? {
        return attribute?.id
    }

    override fun convertToEntityAttribute(dbData: Int?): CommissionStatus? {
        return CommissionStatus.getByIdOrNull(dbData)
    }

}