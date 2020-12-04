package com.hos.service.model.record

data class ValidationRecord(
        val message: String,
        val validationExceptions: Set<ValidationDetailsRecord>
)
