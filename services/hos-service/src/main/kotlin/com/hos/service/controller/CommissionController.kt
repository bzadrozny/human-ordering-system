package com.hos.service.controller

import com.hos.service.model.form.CommissionDecisionForm
import com.hos.service.model.form.CommissionFilterForm
import com.hos.service.model.form.CommissionForm
import com.hos.service.model.record.CommissionBasicRecord
import com.hos.service.model.record.CommissionDetailsRecord
import com.hos.service.service.CommissionService
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("commission")
class CommissionController(private val commissionService: CommissionService) {

    @GetMapping
    fun filteredCommissions(filter: CommissionFilterForm): List<CommissionBasicRecord> {
        return commissionService.findAllCommissions(filter)
    }

    @GetMapping("{id}")
    fun commissionDetails(@PathVariable id: Long): CommissionDetailsRecord {
        return commissionService.findCommissionDetailsById(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    fun createCommission(@RequestBody body: CommissionForm): CommissionDetailsRecord {
        return commissionService.createCommission(body)
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    fun modifyCommission(@PathVariable id: Long, @RequestBody body: CommissionForm): CommissionDetailsRecord {
        val commissionForm = CommissionForm(
                id = id,
                principal = body.principal,
                location = body.location,
                description = body.description,
                status = body.status,
                records = body.records
        )
        return commissionService.modifyCommission(commissionForm)
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    fun deleteCommission(@PathVariable id: Long): CommissionDetailsRecord {
        return commissionService.deleteCommission(id)
    }

    @PostMapping("{id}/send")
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    fun sendCommission(@PathVariable id: Long): CommissionDetailsRecord {
        return commissionService.sendCommission(id)
    }

    @PostMapping("{id}/decision")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'MANAGER')")
    fun sendCommissionDecision(
            @PathVariable id: Long,
            @RequestBody body: CommissionDecisionForm
    ): CommissionDetailsRecord {
        val commissionDecisionForm = CommissionDecisionForm(
                id = id,
                decision = body.decision,
                description = body.description,
                records = body.records
        )
        return commissionService.sendCommissionDecision(commissionDecisionForm)
    }

    @PostMapping("{id}/complete")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'MANAGER')")
    fun completeCommission(@PathVariable id: Long): CommissionDetailsRecord {
        return commissionService.completeCommission(id)
    }

}