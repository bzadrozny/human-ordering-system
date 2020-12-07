package com.hos.service.service

import com.hos.service.model.form.OrganisationForm
import com.hos.service.model.record.OrganisationBasicRecord
import com.hos.service.model.record.OrganisationDetailsRecord

interface OrganisationService {

    fun findAllOrganisations(): List<OrganisationBasicRecord>
    fun findOrganisationById(id: Long): OrganisationDetailsRecord?

    fun registerOrganisation(organisationForm: OrganisationForm): OrganisationDetailsRecord?
    fun modifyOrganisation(organisationForm: OrganisationForm): OrganisationDetailsRecord?

}