package com.hos.service.model.record


data class AddressRecord (
    val id: Long,
    var street: String,
    var number: String,
    var city: String,
    var postalCode: String,
)