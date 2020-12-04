package com.hos.service.model.exception

import com.hos.service.model.common.Validation
import com.hos.service.model.record.ValidationDetailsRecord

class ValidationException(
        private val validation: Validation
) : RuntimeException("Validation of model: ${validation.model} has exceptions with status: ${validation.status}") {

    fun details(): Set<ValidationDetailsRecord> {
        return validation.validations.map { ValidationDetailsRecord(it.message, it.formPath, it.status) }.toSet()
    }

}
