package com.hos.service.validator

import com.hos.service.model.common.Validation

interface FormValidator<F, E> {

    fun validateInitiallyBeforeRegistration(form: F): Validation

    fun validateBeforeRegistration(form: F): Validation

    fun validateInitiallyBeforeModification(form: F): Validation

    fun validateBeforeModification(form: F, entity: E): Validation

}