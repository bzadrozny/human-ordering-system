package com.hos.service.validator.common.impl

import com.hos.service.model.common.Validation
import com.hos.service.model.entity.AddressEntity
import com.hos.service.model.form.AddressForm
import com.hos.service.utils.validateElectiveStringWithSize
import com.hos.service.utils.validateForbiddenField
import com.hos.service.utils.validateRequiredField
import com.hos.service.utils.validateRequiredStringWithSize
import com.hos.service.validator.FormValidator
import org.springframework.stereotype.Component

@Component
class AddressFormValidatorImpl : FormValidator<AddressForm, AddressEntity> {

    override fun validateInitiallyBeforeRegistration(form: AddressForm): Validation {
        val validation = Validation("locationForm")

        validation.addValidation(validateForbiddenField(form.id, "id", "id"))
        validation.addValidation(validateRequiredStringWithSize(form.street, "street", 5, 50, "street"))
        validation.addValidation(validateRequiredStringWithSize(form.number, "number", 1, 10, "number"))
        validation.addValidation(validateRequiredStringWithSize(form.city, "number", 3, 20, "city"))
        validation.addValidation(validateRequiredStringWithSize(form.postalCode, "postalCode", 5, 10, "postalCode"))

        return validation
    }

    override fun validateBeforeRegistration(form: AddressForm): Validation {
        return Validation("locationForm")
    }

    override fun validateInitiallyBeforeModification(form: AddressForm): Validation {
        val validation = Validation("locationForm")

        validation.addValidation(validateRequiredField(form.id, "id", "id"))
        validation.addValidation(validateElectiveStringWithSize(form.street, "street", 5, 50, "street"))
        validation.addValidation(validateElectiveStringWithSize(form.number, "number", 1, 10, "number"))
        validation.addValidation(validateElectiveStringWithSize(form.city, "number", 3, 20, "city"))
        validation.addValidation(validateElectiveStringWithSize(form.postalCode, "postalCode", 5, 10, "postalCode"))

        return validation
    }

    override fun validateBeforeModification(form: AddressForm, entity: AddressEntity): Validation {
        return Validation("locationForm")
    }

}