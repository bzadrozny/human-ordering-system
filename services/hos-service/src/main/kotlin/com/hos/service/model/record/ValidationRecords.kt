package com.hos.service.model.record

import com.hos.service.model.enum.ValidationStatus

data class ValidationRecord(
        val message: String,
        val validationExceptions: Set<ValidationDetailsRecord>
)

data class ValidationDetailsRecord(
        val message: String,
        val formPath: String,
        val status: ValidationStatus
)