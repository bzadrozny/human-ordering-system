package com.hos.service.validator

import com.hos.service.model.common.Validation
import java.time.LocalDate

interface FormValidator<F, E> {

    fun validateInitiallyBeforeRegistration(form: F): Validation

    fun validateComplexBeforeRegistration(form: F): Validation

    fun validateInitiallyBeforeModification(form: F): Validation

    fun validateComplexBeforeModification(form: F, entity: E): Validation

}