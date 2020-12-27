package com.hos.service.model.record

import com.hos.service.model.enum.EntityStatus

data class OrganisationBasicRecord(
        val id: Long,
        val name: String,
        val nip: String,
        val status: EntityStatus
)

data class OrganisationDetailsRecord(
        val id: Long,
        val name: String,
        val nip: String,
        val departments: List<DepartmentRecord>,
        val locations: List<LocationRecord>,
        val status: EntityStatus
)

data class DepartmentRecord(
        val id: Long,
        var name: String,
        val staff: List<UserBasicRecord>,
        var status: EntityStatus
)

data class LocationRecord(
        val id: Long,
        var name: String,
        var registeredOffice: Boolean,
        val address: AddressRecord,
        val organisation: Long,
        var status: EntityStatus
)
