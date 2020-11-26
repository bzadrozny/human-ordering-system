package com.hos.service.model.enum

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.hos.service.model.exception.ResourceNotFoundException

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class ActionType(val id: Int, val desc: String) {

    CREATE(0, "utwórz"),
    UPDATE(1, "edytuj"),
    DELETE(2, "usuń");

    companion object {

        @JsonCreator
        fun getByIdOrNull(@JsonProperty("id") id: Int?): ActionType? {
            return id?.let { getById(id) }
        }

        private fun getById(id: Int): ActionType {
            return values().find { it.id == id }
                    ?: throw  ResourceNotFoundException(Resource.ENUM, QualifierType.ID, "Dictionary '${this::class.simpleName}' with id: $id does not exist")
        }

    }

}