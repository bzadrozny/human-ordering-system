package com.hos.service.model.enum

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.hos.service.model.exception.ResourceNotFoundException

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class OrderStatus(val id: Int, val desc: String) {

    CREATED(0, "zarejestrowane"),
    ACCEPTED(1, "zaakceptowane"),
    DISMISSED(2, "odrzucone"),
    PROCESSING(3, "w trakcie realizacji"),
    CLOSED(4, "zakończone"),
    ;

    companion object {

        @JsonCreator
        fun getByIdOrNull(@JsonProperty("id") id: Int?): OrderStatus? {
            return id?.let { getById(id) }
        }

        private fun getById(id: Int): OrderStatus {
            return values().find { it.id == id }
                    ?: throw  ResourceNotFoundException(Resource.ENUM, QualifierType.ID, "Dictionary '${this::class.simpleName}' with id: $id does not exist")
        }

    }

}
