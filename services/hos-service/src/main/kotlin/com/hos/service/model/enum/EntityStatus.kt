package com.hos.service.model.enum

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.hos.service.model.exception.ResourceNotFoundException

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class EntityStatus(val id: Int, val desc: String) {

    ACTIVE(0, "Aktywny"),
    DISABLED(1, "Nieaktywny"),
    DELETED(2, "Usunięty");

    companion object {
        @JvmStatic
        @JsonCreator //https://github.com/FasterXML/jackson-module-kotlin/issues/75
        fun creator(@JsonProperty("id") id: String): EntityStatus {
            return getById(id.toInt())
        }

        fun getByIdOrNull(@JsonProperty("id") id: Int?): EntityStatus? {
            return id?.let { getById(id.toInt()) }
        }

        private fun getById(id: Int): EntityStatus {
            return values().find { it.id == id }
                    ?: throw  ResourceNotFoundException(Resource.ENUM, QualifierType.ID, "'UserStatus' with id: $id does not exist")
        }
    }
}