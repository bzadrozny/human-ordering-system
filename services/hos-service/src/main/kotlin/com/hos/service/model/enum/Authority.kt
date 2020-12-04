package com.hos.service.model.enum

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeId
import com.hos.service.model.exception.ResourceNotFoundException
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class Authority(val id: Int, val desc: String) {

    ANONYMOUS(-1, "Guest"),
    ADMIN(0, "Administrator"),
    DIRECTOR(1, "Director"),
    MANAGER(2, "Manager"),
    RECRUTER(3, "Recruter"),
    CLIENT(4, "Klinet"),
    ;

    companion object {
        @JvmStatic
        @JsonCreator //https://github.com/FasterXML/jackson-module-kotlin/issues/75
        fun creator(@JsonProperty("id") id: String): Authority {
            return getById(id.toInt())
        }

        fun getByIdOrNull(@JsonProperty("id") id: Int?): Authority? {
            return id?.let { getById(id.toInt()) }
        }

        private fun getById(id: Int): Authority {
            return values().find { it.id == id }
                    ?: throw  ResourceNotFoundException(Resource.ENUM, QualifierType.ID, "'Authority' with id: $id does not exist")
        }
    }

}