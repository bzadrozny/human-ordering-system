package com.hos.service.model.common

import com.hos.service.model.enum.ValidationStatus

class Validation(
        val model: String,
        var status: ValidationStatus = ValidationStatus.OK,
        val validations: MutableSet<ValidationDetails> = mutableSetOf()
) {

    fun addValidation(message: String, formPath: String, status: ValidationStatus) {
        addValidation(ValidationDetails(message, formPath, status))
    }

    fun addValidation(details: ValidationDetails?) {
        if (details == null) return
        if (details.status == ValidationStatus.BLOCKER) {
            status = ValidationStatus.BLOCKER
        } else if (status != ValidationStatus.BLOCKER && details.status == ValidationStatus.WARNING) {
            status = ValidationStatus.WARNING
        }
        validations.add(details)
    }

    fun addValidation(validation: Validation) {
        if (validation.hasBlocker()) {
            status = ValidationStatus.BLOCKER
        } else if (status != ValidationStatus.BLOCKER && validation.hasWarning()) {
            status = ValidationStatus.WARNING
        }
        validations.addAll(validation.validations)
    }

    val hasBlocker: () -> Boolean = { validations.any { it.status == ValidationStatus.BLOCKER } }
    val hasWarning: () -> Boolean = { validations.any { it.status == ValidationStatus.WARNING } }

}