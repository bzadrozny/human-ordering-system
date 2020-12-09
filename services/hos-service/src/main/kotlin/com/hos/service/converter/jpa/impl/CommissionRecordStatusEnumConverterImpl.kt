package com.hos.service.converter.jpa.impl

import com.hos.service.model.enum.CommissionRecordStatus
import com.hos.service.model.enum.CommissionStatus
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class CommissionRecordStatusEnumConverterImpl : AttributeConverter<CommissionRecordStatus, Int> {

    override fun convertToDatabaseColumn(attribute: CommissionRecordStatus?): Int? {
        return attribute?.id
    }

    override fun convertToEntityAttribute(dbData: Int?): CommissionRecordStatus? {
        return CommissionRecordStatus.getByIdOrNull(dbData)
    }

}