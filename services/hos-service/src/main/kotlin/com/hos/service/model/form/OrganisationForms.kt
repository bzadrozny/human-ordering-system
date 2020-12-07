package com.hos.service.model.form

import com.hos.service.model.enum.EntityStatus

class OrganisationForm(
        val id: Long? = null,
        val name: String? = null,
        val nip: String? = null,
        val departments: Set<DepartmentForm>? = null,
        val locations: Set<LocationForm>? = null,
        val status: EntityStatus? = null
)

class DepartmentForm(
        val id: Long? = null,
        val name: String? = null,
        val status: EntityStatus? = null,
        val organisation: Long? = null
)

class LocationForm(
        val id: Long? = null,
        val name: String? = null,
        val registeredOffice: Boolean? = null,
        val address: AddressForm? = null,
        val status: EntityStatus? = null,
        val organisation: Long? = null
)