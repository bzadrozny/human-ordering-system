package com.hos.service.model.validator

import com.hos.service.model.common.Validation

interface FormValidator<F, E> {

    fun validateBeforeRegistration(form: F): Validation

    fun validateInitiallyModification(form: F): Validation

    fun validateBeforeRegisterModification(form: F, entity: E): Validation

}