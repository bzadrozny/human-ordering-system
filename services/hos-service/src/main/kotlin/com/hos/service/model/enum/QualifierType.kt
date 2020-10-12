package com.hos.service.model.enum

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.hos.service.model.exception.ResourceNotFoundException

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class QualifierType(val id: Int, val desc: String) {

    ID(0, "id"),
    LOGIN(1, "login"),
    NAME(2, "name");


    companion object {
        @JsonCreator
        fun getByIdOrNull(@JsonProperty("id") id: Int?): QualifierType? {
            return id?.let { getById(id) }
        }

        private fun getById(id: Int): QualifierType {
            return values().find { it.id == id }
                    ?: throw  ResourceNotFoundException(Resource.ENUM, ID, "Dictionary '${this::class.simpleName}' with id: $id does not exist")
        }
    }

}

