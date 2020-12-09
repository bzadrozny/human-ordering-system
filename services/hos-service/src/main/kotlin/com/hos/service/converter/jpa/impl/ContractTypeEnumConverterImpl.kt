package com.hos.service.converter.jpa.impl

import com.hos.service.model.enum.ContractType
import com.hos.service.model.enum.SettlementType
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class ContractTypeEnumConverterImpl : AttributeConverter<ContractType, Int> {

    override fun convertToDatabaseColumn(attribute: ContractType?): Int? {
        return attribute?.id
    }

    override fun convertToEntityAttribute(dbData: Int?): ContractType? {
        return ContractType.getByIdOrNull(dbData)
    }

}