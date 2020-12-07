package com.hos.service.controller

import com.hos.service.model.form.CommissionDecisionForm
import com.hos.service.model.form.CommissionFilterForm
import com.hos.service.model.form.CommissionForm
import com.hos.service.model.record.CommissionBasicRecord
import com.hos.service.model.record.CommissionDetailsRecord
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("commission")
class CommissionController {

    @GetMapping
    fun filteredCommissions(@RequestParam filter: CommissionFilterForm): List<CommissionBasicRecord> {

        TODO("Not implemented yet")

    }

    @GetMapping("{id}")
    fun commissionDetails(@PathVariable id: Long): CommissionDetailsRecord {

        TODO("Not implemented yet")

    }

    @PostMapping
    fun createCommission(@RequestBody body: CommissionForm): CommissionDetailsRecord {

        TODO("Not implemented yet")

    }

    @PutMapping("{id}")
    fun modifyCommission(@PathVariable id: Long, @RequestBody body: CommissionForm): CommissionDetailsRecord {

        TODO("Not implemented yet")

    }

    @PostMapping("{id}/send")
    fun sendCommission(@PathVariable id: Long): CommissionDetailsRecord {

        TODO("Not implemented yet")

    }

    @PostMapping("{id}/decision")
    fun createCommissionDecision(@PathVariable id: Long, @RequestBody body: CommissionDecisionForm): CommissionDetailsRecord {

        TODO("Not implemented yet")

    }

    @PostMapping("{id}/complete")
    fun completeCommission(@PathVariable id: Long): CommissionDetailsRecord {

        TODO("Not implemented yet")

    }

}