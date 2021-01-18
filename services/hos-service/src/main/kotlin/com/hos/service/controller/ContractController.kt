package com.hos.service.controller

import com.hos.service.model.form.ContractForm
import com.hos.service.model.record.ContractDetailsRecord
import com.hos.service.service.ContractService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("commission/{commissionId}/records/{recordId}/contract")
class ContractController(private val contractService: ContractService) {

    @GetMapping("{contractId}")
    fun contractDetails(
            @PathVariable commissionId: Long,
            @PathVariable recordId: Long,
            @PathVariable contractId: Long
    ): ContractDetailsRecord {
        return contractService.findContractDetailsById(contractId)
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RECRUITER')")
    fun registerContract(
            @PathVariable commissionId: Long,
            @PathVariable recordId: Long,
            @RequestBody body: ContractForm
    ): ContractDetailsRecord {
        body.commission = commissionId
        body.commissionRecord = recordId
        return contractService.registerContract(body)
    }

    @PutMapping("{contractId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RECRUITER')")
    fun modifyContract(
            @PathVariable contractId: Long,
            @PathVariable commissionId: Long,
            @PathVariable recordId: Long,
            @RequestBody body: ContractForm
    ): ContractDetailsRecord {
        body.id = contractId
        body.commission = commissionId
        body.commissionRecord = recordId
        return contractService.modifyContract(body)
    }

}