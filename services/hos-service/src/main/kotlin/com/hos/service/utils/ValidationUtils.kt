package com.hos.service.utils

import com.hos.service.model.common.ValidationDetails
import com.hos.service.model.enum.ValidationStatus

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

fun validateRequiredFieldWarning(field: Any?, name: String, fieldPath: String): ValidationDetails? {
    return if (field == null) ValidationDetails(
            "Czy na pewno pole '$name' ma być puste?",
            fieldPath,
            ValidationStatus.WARNING
    ) else null
}

fun validateRequiredStringWithSize(field: String?, name: String, min: Int, max: Int, fieldPath: String): ValidationDetails? {
    return if (field == null || field.length !in min..max) ValidationDetails(
            "Pole '$name' jest wymagane i musi być o długości od $min do $max",
            fieldPath,
            ValidationStatus.BLOCKER
    ) else null
}

fun validateElectiveStringWithSize(field: String?, name: String, min: Int, max: Int, fieldPath: String): ValidationDetails? {
    return if (field != null && field.length !in min..max) ValidationDetails(
            "Pole '$name' musi być o długości od $min do $max",
            fieldPath,
            ValidationStatus.BLOCKER
    ) else null
}

fun validateRequiredCollectionWithSize(field: Collection<Any>?, name: String, min: Int, max: Int, fieldPath: String): ValidationDetails? {
    return if (field == null || field.size !in min..max) ValidationDetails(
            "Kolekcja '$name' jest wymagana i musi zawierać od $min do $max elementów",
            fieldPath,
            ValidationStatus.BLOCKER
    ) else null
}

fun validateRequiredCollectionWithSize(field: Collection<Any>?, name: String, min: Int, fieldPath: String): ValidationDetails? {
    return if (field == null || field.size < min) ValidationDetails(
            "Kolekcja '$name' jest wymagana i musi zawierać co najmniej $min elementów",
            fieldPath,
            ValidationStatus.BLOCKER
    ) else null
}

fun validateElectiveCollectionWithSize(field: Collection<Any>?, name: String, min: Int, fieldPath: String): ValidationDetails? {
    return if (field != null && field.size < min) ValidationDetails(
            "Kolekcja '$name' jest wymagana i musi zawierać co najmniej $min elementów",
            fieldPath,
            ValidationStatus.BLOCKER
    ) else null
}

fun validateRequiredFieldFromCollection(field: Any?, name: String, availableValues: List<Any>, fieldPath: String): ValidationDetails? {
    return if (field == null || !availableValues.contains(field)) ValidationDetails(
            "Pole '$name' jest wymagane nie może przyjąć wartości: '${field}'",
            fieldPath,
            ValidationStatus.BLOCKER
    ) else null
}

fun validateElectiveFieldFromCollection(field: Any?, name: String, availableValues: List<Any>, fieldPath: String): ValidationDetails? {
    return if (field != null && !availableValues.contains(field)) ValidationDetails(
            "Pole '$name' nie może przyjąć wartości: '${field}'",
            fieldPath,
            ValidationStatus.BLOCKER
    ) else null
}