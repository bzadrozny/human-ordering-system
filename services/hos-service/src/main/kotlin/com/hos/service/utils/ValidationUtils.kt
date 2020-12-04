package com.hos.service.utils

import com.hos.service.model.common.ValidationDetails
import com.hos.service.model.enum.ValidationStatus

fun validateForbiddenField(field: Any?, name: String, fieldPath: String): ValidationDetails? {
    return if (field != null) ValidationDetails(
            "Pole $name jest niedozwolone",
            fieldPath,
            ValidationStatus.BLOCKER
    ) else null
}

fun validateRequiredField(field: Any?, name: String, fieldPath: String): ValidationDetails? {
    return if (field == null) ValidationDetails(
            "Pole $name wymagane",
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
            "Pole $name jest wymagane i musi być o długości od $min do $max",
            fieldPath,
            ValidationStatus.BLOCKER
    ) else null
}

fun validateElectiveStringWithSize(field: String?, name: String, min: Int, max: Int, fieldPath: String): ValidationDetails? {
    return if (field?.length !in min..max) ValidationDetails(
            "Pole $name musi być o długości od $min do $max",
            fieldPath,
            ValidationStatus.BLOCKER
    ) else null
}

fun validateRequiredCollectionWithSize(field: Collection<Any>?, name: String, min: Int, max: Int, fieldPath: String): ValidationDetails? {
    return if (field == null || field.size !in min..max) ValidationDetails(
            "Kolekcja $name jest wymagana i musi zawierać od $min do $max elementów",
            fieldPath,
            ValidationStatus.BLOCKER
    ) else null
}

fun validateElectiveCollectionWithSize(field: Collection<Any>?, name: String, min: Int, max: Int, fieldPath: String): ValidationDetails? {
    return if (field?.size in min..max) ValidationDetails(
            "Kolekcja $name jest wymagana i musi zawierać od $min do $max elementów",
            fieldPath,
            ValidationStatus.BLOCKER
    ) else null
}