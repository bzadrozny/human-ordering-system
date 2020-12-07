package com.hos.service.converter.jpa.impl

import com.hos.service.model.enum.EntityStatus
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class EntityStatusEnumConverterImpl : AttributeConverter<EntityStatus, Int> {

    override fun convertToDatabaseColumn(attribute: EntityStatus?): Int? {
        return attribute?.id
    }

    override fun convertToEntityAttribute(dbData: Int?): EntityStatus? {
        return EntityStatus.getByIdOrNull(dbData)
    }

}