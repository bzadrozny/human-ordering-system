package com.hos.service.model.enum

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.hos.service.model.exception.ResourceNotFoundException

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class CommissionStatus(val id: Int, val desc: String) {

    CREATED(0, ""),
    MODIFIED(1, ""),
    SENT(2, ""),
    REJECTED(-1, ""),
    DELETED(-2, ""),
    ACCEPTED(3, ""),
    EXECUTION(4, ""),
    COMPLETED(5, "");

    companion object {
        @JvmStatic
        @JsonCreator //https://github.com/FasterXML/jackson-module-kotlin/issues/75
        fun creator(@JsonProperty("id") id: String): CommissionStatus {
            return getById(id.toInt())
        }

        fun getByIdOrNull(@JsonProperty("id") id: Int?): CommissionStatus? {
            return id?.let { getById(id.toInt()) }
        }

        private fun getById(id: Int): CommissionStatus {
            return values().find { it.id == id }
                    ?: throw  ResourceNotFoundException(Resource.ENUM, QualifierType.ID, "'CommissionStatus' with id: $id does not exist")
        }
    }


}