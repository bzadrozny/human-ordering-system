package com.hos.service.model.enum

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeId
import com.hos.service.model.exception.ResourceNotFoundException

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class Authority(val id: Int, val desc: String) {

    ANONYMOUS(-1, "Guest"),
    ADMIN(0, "Administrator"),
    MANAGER(1, "Manager");

    companion object {
        @JsonCreator
        fun getByIdOrNull(@JsonProperty("id") id: Int?): Authority? {
            return id?.let { getById(id) }
        }

        private fun getById(id: Int): Authority {
            return values().find { it.id == id }
                    ?: throw  ResourceNotFoundException(Resource.ENUM, QualifierType.ID, "Dictionary 'Authorities' with id: $id does not exist")
        }
    }

}