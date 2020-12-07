package com.hos.service.service

import com.hos.service.model.form.DepartmentForm
import com.hos.service.model.record.OrganisationDetailsRecord

interface DepartmentService {

    fun addDepartment(form: DepartmentForm): OrganisationDetailsRecord

    fun modifyDepartment(form: DepartmentForm): OrganisationDetailsRecord

}