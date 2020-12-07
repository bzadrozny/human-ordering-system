package com.hos.service.model.enum

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.hos.service.model.exception.ResourceNotFoundException

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class CommissionDecision(val id: Int, val desc: String) {

    ACCEPTED(0, "Zaakceptowano"),
    MODIFIED(1, "Zmodyfikowano"),
    REJECTED(2, "Odrzucono");

    companion object {
        @JvmStatic
        @JsonCreator //https://github.com/FasterXML/jackson-module-kotlin/issues/75
        fun creator(@JsonProperty("id") id: String): CommissionDecision {
            return getById(id.toInt())
        }

        fun getByIdOrNull(@JsonProperty("id") id: Int?): CommissionDecision? {
            return id?.let { getById(id.toInt()) }
        }

        private fun getById(id: Int): CommissionDecision {
            return values().find { it.id == id }
                    ?: throw  ResourceNotFoundException(Resource.ENUM, QualifierType.ID, "'CommissionDecision' with id: $id does not exist")
        }
    }


}