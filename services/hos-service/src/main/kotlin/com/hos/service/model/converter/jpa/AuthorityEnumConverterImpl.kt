package com.hos.service.model.converter.jpa

import com.hos.service.model.enum.Authority
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class AuthorityEnumConverterImpl : AttributeConverter<Authority, Int> {

    override fun convertToDatabaseColumn(attribute: Authority?): Int? {
        return attribute?.id
    }

    override fun convertToEntityAttribute(dbData: Int?): Authority? {
        return Authority.getByIdOrNull(dbData)
    }

}