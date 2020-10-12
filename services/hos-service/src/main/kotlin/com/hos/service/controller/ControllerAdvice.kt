package com.hos.service.controller

import com.hos.service.model.exception.ResourceNotFoundException
import com.hos.service.model.record.ExceptionRecord
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFound(exception: ResourceNotFoundException): ResponseEntity<ExceptionRecord> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionRecord(exception.localizedMessage, exception.resourceType, HttpStatus.NOT_FOUND))
    }

}