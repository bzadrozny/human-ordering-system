package com.hos.service.controller

import com.hos.service.model.form.DepartmentForm
import com.hos.service.model.record.OrganisationDetailsRecord
import com.hos.service.service.DepartmentService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("organisation/{organisationId}/departments")
class DepartmentController(private val departmentService: DepartmentService) {

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'MANAGER')")
    fun addDepartment(@PathVariable organisationId: Long, @RequestBody form: DepartmentForm): OrganisationDetailsRecord {
        val departmentForm = DepartmentForm(
                name = form.name,
                status = form.status,
                organisation = organisationId
        )
        return departmentService.addDepartment(departmentForm)
    }

    @PutMapping("/{departmentId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'MANAGER')")
    fun modifyDepartment(
            @PathVariable organisationId: Long,
            @PathVariable departmentId: Long,
            @RequestBody form: DepartmentForm): OrganisationDetailsRecord {
        val departmentForm = DepartmentForm(
                departmentId,
                name = form.name,
                status = form.status,
                organisation = organisationId
        )
        return departmentService.modifyDepartment(departmentForm)
    }

}