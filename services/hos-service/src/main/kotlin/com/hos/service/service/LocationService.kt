package com.hos.service.service

import com.hos.service.model.form.LocationForm
import com.hos.service.model.record.OrganisationDetailsRecord

interface LocationService {

    fun addLocation(form: LocationForm): OrganisationDetailsRecord

    fun modifyLocation(form: LocationForm): OrganisationDetailsRecord

}