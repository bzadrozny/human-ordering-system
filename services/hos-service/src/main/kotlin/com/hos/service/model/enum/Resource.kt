package com.hos.service.model.enum

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.hos.service.model.exception.ResourceNotFoundException

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class Resource(val id: Int, val desc: String) {

    USER(0, "User"),
    ENUM(1, "Enum"),
    ORGANISATION(2, "Organisation"),
    ADMINISTRATION_UNIT(3, "Administration unit"),
    LOCATION(4, "Location"),
    DEPARTMENT(5, "Department"),
    COMMISSION(6, "Commission"),
    COMMISSION_RECORD(7, "Commission record"),
    CONTRACT(8, "Contract"),
    ;


    companion object {
        @JsonCreator
        fun getByIdOrNull(@JsonProperty("id") id: Int?): Resource? {
            return id?.let { getById(id) }
        }

        private fun getById(id: Int): Resource {
            return values().find { it.id == id }
                    ?: throw  ResourceNotFoundException(ENUM, QualifierType.ID, "Dictionary '${this::class.simpleName}' with id: $id does not exist")
        }
    }

}