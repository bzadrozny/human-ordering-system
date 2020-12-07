package com.hos.service.controller

import com.hos.service.model.form.ContractForm
import com.hos.service.model.record.CommissionDetailsRecord
import com.hos.service.model.record.ContractDetailsRecord
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("commission/{commissionId}/contract")
class ContractController {

    @GetMapping("{contractId}")
    fun contractDetails(@PathVariable commissionId: Long, @PathVariable contractId: Long): ContractDetailsRecord {

        TODO("Not implemented yet")

    }

    @PostMapping
    fun registerContract(@PathVariable commissionId: Long, @RequestBody body: ContractForm): CommissionDetailsRecord {

        TODO("Not implemented yet")

    }

    @PutMapping("{contractId}")
    fun modifyContract(
            @PathVariable commissionId: Long,
            @PathVariable contractId: Long,
            @RequestBody body: ContractForm
    ): ContractDetailsRecord {

        TODO("Not implemented yet")

    }

    @DeleteMapping("{id}")
    fun deleteContract(@PathVariable id: Long): ResponseEntity<Any> {

        TODO("Not implemented yet")

    }

}