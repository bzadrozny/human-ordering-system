package com.hos.service.controller

import com.hos.service.model.form.OrganisationForm
import com.hos.service.model.record.OrganisationBasicRecord
import com.hos.service.model.record.OrganisationDetailsRecord
import com.hos.service.service.OrganisationService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("organisation")
class OrganisationController(private val organisationService: OrganisationService) {

    @GetMapping
    @PreAuthorize("!hasAuthority('CLIENT')")
    fun allOrganisations(): List<OrganisationBasicRecord> {
        return organisationService.findAllOrganisations()
    }

    @GetMapping("/{id}")
    fun organisationDetails(@PathVariable id: Long): OrganisationDetailsRecord? {
        return organisationService.findOrganisationById(id)
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR')")
    fun registerOrganisation(@RequestBody body: OrganisationForm): OrganisationDetailsRecord? {
        return organisationService.registerOrganisation(body)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR')")
    fun modifyOrganisation(@PathVariable id: Long, @RequestBody body: OrganisationForm): OrganisationDetailsRecord? {
        val organisationForm = OrganisationForm(
                id = id,
                name = body.name,
                nip = body.nip,
                departments = body.departments,
                locations = body.locations,
                status = body.status
        )
        return organisationService.modifyOrganisation(organisationForm)
    }

}