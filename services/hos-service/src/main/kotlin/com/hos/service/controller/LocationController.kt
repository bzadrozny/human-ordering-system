package com.hos.service.controller

import com.hos.service.model.form.LocationForm
import com.hos.service.model.record.OrganisationDetailsRecord
import com.hos.service.service.LocationService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("organisation/{organisationId}/locations")
class LocationController(private val locationService: LocationService) {

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'MANAGER')")
    fun addDepartment(@PathVariable organisationId: Long, @RequestBody form: LocationForm): OrganisationDetailsRecord? {
        val locationForm = LocationForm(
                name = form.name,
                registeredOffice = form.registeredOffice,
                address = form.address,
                status = form.status,
                organisation = organisationId
        )
        return locationService.addLocation(locationForm)
    }

    @PutMapping("/{locationId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'MANAGER')")
    fun modifyDepartment(
            @PathVariable organisationId: Long,
            @PathVariable locationId: Long,
            @RequestBody form: LocationForm
    ): OrganisationDetailsRecord? {
        val locationForm = LocationForm(
                locationId,
                form.name,
                form.registeredOffice,
                form.address,
                form.status,
                organisationId
        )
        return locationService.modifyLocation(locationForm)
    }

}