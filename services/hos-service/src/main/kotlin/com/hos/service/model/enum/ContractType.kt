package com.hos.service.model.enum

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.hos.service.model.exception.ResourceNotFoundException

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class ContractType(val id: Int, val desc: String) {

    LABOR_CONTRACT(0, "Umowa o pracę"),
    ORDER_CONTRACT(1, "Umowa zlecenie");

    companion object {
        @JvmStatic
        @JsonCreator //https://github.com/FasterXML/jackson-module-kotlin/issues/75
        fun creator(@JsonProperty("id") id: String): ContractType {
            return getById(id.toInt())
        }

        fun getByIdOrNull(id: Int?): ContractType? {
            return id?.let { getById(id) }
        }

        private fun getById(id: Int): ContractType {
            return values().find { it.id == id }
                    ?: throw  ResourceNotFoundException(Resource.ENUM, QualifierType.ID, "Dictionary '${this::class.simpleName}' with id: $id does not exist")
        }
    }

}
