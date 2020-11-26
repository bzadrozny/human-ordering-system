package com.hos.service.model.enum

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.hos.service.model.exception.ResourceNotFoundException

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class OrderRecordStatus (val id: Int, val desc: String) {

    PER_HOUR(0, "/godz."),
    PER_MONTH(1, "/msc."),
    PER_JOB(2, "/contract");

    companion object {

        @JsonCreator
        fun getByIdOrNull(@JsonProperty("id") id: Int?): OrderRecordStatus? {
            return id?.let { getById(id) }
        }

        private fun getById(id: Int): OrderRecordStatus {
            return values().find { it.id == id }
                    ?: throw  ResourceNotFoundException(Resource.ENUM, QualifierType.ID, "Dictionary '${this::class.simpleName}' with id: $id does not exist")
        }

    }

}