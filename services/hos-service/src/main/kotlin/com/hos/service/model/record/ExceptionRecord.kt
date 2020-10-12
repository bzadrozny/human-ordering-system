package com.hos.service.model.record

import com.hos.service.model.enum.Resource
import org.springframework.http.HttpStatus

data class ExceptionRecord(
        val message: String,
        val resourceType: Resource,
        val httpStatus: HttpStatus
)