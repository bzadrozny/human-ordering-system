package com.hos.service.model.common

import com.hos.service.model.enum.ValidationStatus

class ValidationDetails(
        val message: String,
        val formPath: String,
        val status: ValidationStatus
)