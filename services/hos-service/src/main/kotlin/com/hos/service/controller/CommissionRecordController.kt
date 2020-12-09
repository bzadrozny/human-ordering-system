package com.hos.service.controller

import com.hos.service.model.form.CommissionRecordForm
import com.hos.service.model.record.CommissionRecordDetailsRecord
import com.hos.service.service.CommissionRecordService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("commission/{commissionId}/records")
class CommissionRecordController(private val recordService: CommissionRecordService) {

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    fun addCommissionRecord(
            @PathVariable commissionId: Long,
            @RequestBody body: CommissionRecordForm
    ): CommissionRecordDetailsRecord {
        body.commission = commissionId
        return recordService.addRecord(body)
    }

    @PutMapping("{commissionRecordId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    fun modifyCommissionRecord(
            @PathVariable commissionId: Long,
            @PathVariable commissionRecordId: Long,
            @RequestBody body: CommissionRecordForm
    ): CommissionRecordDetailsRecord {
        body.id = commissionRecordId
        body.commission = commissionId
        return recordService.modifyRecord(body)
    }

    @DeleteMapping("{commissionRecordId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    fun cancelCommissionRecord(
            @PathVariable commissionId: Long,
            @PathVariable commissionRecordId: Long,
    ): ResponseEntity<Any?> {
        recordService.cancelRecord(commissionId, commissionRecordId)
        return ResponseEntity.ok().build()
    }

}