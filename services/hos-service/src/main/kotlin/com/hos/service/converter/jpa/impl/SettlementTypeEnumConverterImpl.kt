package com.hos.service.converter.jpa.impl

import com.hos.service.model.enum.SettlementType
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class SettlementTypeEnumConverterImpl : AttributeConverter<SettlementType, Int> {

    override fun convertToDatabaseColumn(attribute: SettlementType?): Int? {
        return attribute?.id
    }

    override fun convertToEntityAttribute(dbData: Int?): SettlementType? {
        return SettlementType.getByIdOrNull(dbData)
    }

}