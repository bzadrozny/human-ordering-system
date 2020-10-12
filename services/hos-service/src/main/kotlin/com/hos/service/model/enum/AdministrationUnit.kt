package com.hos.service.model.enum

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.hos.service.model.exception.ResourceNotFoundException

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class AdministrationUnit(val id: Int) {

    ADMIN(0),
    MANAGER(1),
    HR(2),
    CLIENT(3);

    companion object {
        @JsonCreator
        fun getByIdOrNull(@JsonProperty("id") id: Int?): AdministrationUnit? {
            return id?.let { getById(id) }
        }

        private fun getById(id: Int): AdministrationUnit {
            return values().find { it.id == id }
                    ?: throw  ResourceNotFoundException(Resource.ENUM, QualifierType.ID, "Value of dictionary 'Administration Units' with id: $id does not exist")
        }
    }
}