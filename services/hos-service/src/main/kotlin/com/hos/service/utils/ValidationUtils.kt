package com.hos.service.utils

import com.hos.service.model.common.ValidationDetails
import com.hos.service.model.enum.ValidationStatus
import java.time.LocalDate

fun validateForbiddenField(field: Any?, name: String, fieldPath: String): ValidationDetails? {
    return if (field != null) ValidationDetails(
        "Pole '$name' jest niedozwolone",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}

fun validateRequiredField(field: Any?, name: String, fieldPath: String): ValidationDetails? {
    return if (field == null) ValidationDetails(
        "Pole '$name' wymagane",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}

fun validateRequiredFieldWithValue(field: Any?, name: String, expected: Any, fieldPath: String): ValidationDetails? {
    return if (field == null || field != expected) ValidationDetails(
        "Pole '$name' wymagane i musi być równe '$expected'",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}

fun validateRequiredFieldWithValueWarning(
    field: Any?,
    name: String,
    expected: Any,
    fieldPath: String
): ValidationDetails? {
    return if (field == null || field != expected) ValidationDetails(
        "Pole '$name' wymagane i oczekiwana wartość to '$expected', czy na pewno wprowadzić wartość: '$field'?",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}

fun validateRequiredFieldWarning(field: Any?, name: String, fieldPath: String): ValidationDetails? {
    return if (field == null) ValidationDetails(
        "Czy na pewno pole '$name' ma być puste?",
        fieldPath,
        ValidationStatus.WARNING
    ) else null
}

fun validateRequiredStringWithSize(
    field: String?,
    name: String,
    min: Int,
    max: Int,
    fieldPath: String
): ValidationDetails? {
    return if (field == null || field.length !in min..max) ValidationDetails(
        "Pole '$name' jest wymagane i musi być o długości od $min do $max",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}

fun validateElectiveStringWithSize(
    field: String?,
    name: String,
    min: Int,
    max: Int,
    fieldPath: String
): ValidationDetails? {
    return if (field != null && field.length !in min..max) ValidationDetails(
        "Pole '$name' musi być o długości od $min do $max",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}

fun validateRequiredNumberInRange(
    field: Number?,
    name: String,
    min: Int,
    max: Int,
    fieldPath: String
): ValidationDetails? {
    return if (field == null || field !in min..max) ValidationDetails(
        "Pole '$name' jest wymagane i musi mieścić się w zakresie od $min do $max",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}

fun validateRequiredNumberInRange(
    field: Number?,
    name: String,
    min: Double,
    max: Double,
    fieldPath: String
): ValidationDetails? {
    return if (field == null || field.toDouble() !in min..max) ValidationDetails(
        "Pole '$name' jest wymagane i musi mieścić się w zakresie od $min do $max",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}

fun validateRequiredNumberBiggerThan(
    field: Number?,
    name: String,
    min: Double?,
    fieldPath: String,
    minName: String? = null
): ValidationDetails? {
    return if (field == null || (min != null && field.toDouble() < min)) ValidationDetails(
        "Pole '$name' jest wymagane i musi być większe od ${minName ?: min}",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}

fun validateRequiredCollectionWithSize(
    field: Collection<Any>?,
    name: String,
    min: Int,
    max: Int,
    fieldPath: String
): ValidationDetails? {
    return if (field == null || field.size !in min..max) ValidationDetails(
        "Kolekcja '$name' jest wymagana i musi zawierać od $min do $max elementów",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}

fun validateElectiveCollectionWithSize(
    field: Collection<Any>?,
    name: String,
    min: Int,
    max: Int,
    fieldPath: String
): ValidationDetails? {
    return if (field != null && field.size !in min..max) ValidationDetails(
        "Kolekcja '$name' jest wymagana i musi zawierać od $min do $max elementów",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}

fun validateRequiredCollectionWithMinSize(
    field: Collection<Any>?,
    name: String,
    min: Int,
    fieldPath: String
): ValidationDetails? {
    return if (field == null || field.size < min) ValidationDetails(
        "Kolekcja '$name' jest wymagana i musi zawierać co najmniej $min elementów",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}

fun validateElectiveCollectionWithMinSize(
    field: Collection<Any>?,
    name: String,
    min: Int,
    fieldPath: String
): ValidationDetails? {
    return if (field != null && field.size < min) ValidationDetails(
        "Kolekcja '$name' jest wymagana i musi zawierać co najmniej $min elementów",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}

fun validateRequiredFieldFromCollection(
    field: Any?,
    name: String,
    availableValues: List<Any>,
    fieldPath: String
): ValidationDetails? {
    return if (field == null || !availableValues.contains(field)) ValidationDetails(
        "Pole '$name' jest wymagane nie może przyjąć wartości: '$field'",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}

fun validateElectiveFieldFromCollection(
    field: Any?,
    name: String,
    availableValues: List<Any>,
    fieldPath: String
): ValidationDetails? {
    return if (field != null && !availableValues.contains(field)) ValidationDetails(
        "Pole '$name' nie może przyjąć wartości: '$field'",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}

fun validateRequiredDateNotBefore(
    field: LocalDate?,
    name: String,
    minDate: LocalDate?,
    minDateName: String,
    fieldPath: String
): ValidationDetails? {
    return if (field == null || minDate?.let { field.isBefore(it) } == true) ValidationDetails(
        "Data '$name' jest obowiązkowa i nie może być wcześniejsza niż: '$minDateName'",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}

fun validateElectiveDateNotBefore(
    field: LocalDate?,
    name: String,
    minDate: LocalDate?,
    minDateName: String,
    fieldPath: String
): ValidationDetails? {
    return if (field != null && minDate?.let { field.isBefore(it) } == true) ValidationDetails(
        "Data '$name' nie może być wcześniejsza niż: '$minDateName'",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}

fun validateRequiredDateNotAfter(
    field: LocalDate?,
    name: String,
    maxDate: LocalDate?,
    maxDateName: String,
    fieldPath: String
): ValidationDetails? {
    return if (field == null || maxDate?.let { field.isAfter(it) } == true) ValidationDetails(
        "Data '$name' jest obowiązkowa i nie może być późniejsza niż: '$maxDateName'",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}

fun validateElectiveDateNotAfter(
    field: LocalDate?,
    name: String,
    maxDate: LocalDate?,
    maxDateName: String,
    fieldPath: String
): ValidationDetails? {
    return if (field != null && maxDate?.let { field.isAfter(it) } == true) ValidationDetails(
        "Data '$name' nie może być późniejsza niż: '$maxDateName'",
        fieldPath,
        ValidationStatus.BLOCKER
    ) else null
}