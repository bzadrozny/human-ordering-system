package com.hos.service.model.enum

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.hos.service.model.exception.ResourceNotFoundException

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class CommissionRecordStatus(val id: Int, val desc: String) {

    ACCEPTED(0, "Zaakceptowano"),
    MODIFIED(1, "Zmodyfikowano"),
    REJECTED(2, "Odrzucono"),
    DELETED(3, "Usunięto");

    companion object {
        @JvmStatic
        @JsonCreator //https://github.com/FasterXML/jackson-module-kotlin/issues/75
        fun creator(@JsonProperty("id") id: String): CommissionRecordStatus {
            return getById(id.toInt())
        }

        fun getByIdOrNull(id: Int?): CommissionRecordStatus? {
            return id?.let { getById(id) }
        }

        private fun getById(id: Int): CommissionRecordStatus {
            return values().find { it.id == id }
                    ?: throw  ResourceNotFoundException(Resource.ENUM, QualifierType.ID, "Dictionary '${this::class.simpleName}' with id: $id does not exist")
        }
    }

}
