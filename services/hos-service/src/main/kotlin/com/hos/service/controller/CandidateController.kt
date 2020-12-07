package com.hos.service.controller

import com.hos.service.model.form.CandidateForm
import com.hos.service.model.form.CandidatesFilterForm
import com.hos.service.model.form.CommissionFilterForm
import com.hos.service.model.record.CandidateBasicRecord
import com.hos.service.model.record.CandidateDetailsRecord
import com.hos.service.model.record.CommissionBasicRecord
import com.hos.service.model.record.CommissionDetailsRecord
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("candidate")
class CandidateController {

    @GetMapping
    fun filteredCandidates(@RequestParam filter: CandidatesFilterForm): List<CandidateBasicRecord> {

        TODO("Not implemented yet")

    }

    @GetMapping("{id}")
    fun candidateDetails(@PathVariable id: Long): CandidateDetailsRecord {

        TODO("Not implemented yet")

    }

    @PostMapping
    fun addCandidate(@RequestBody body: CandidateForm): CommissionDetailsRecord {

        TODO("Not implemented yet")

    }

    @PutMapping("{id}")
    fun modifyCandidate(@PathVariable id: Long, @RequestBody body: CandidateForm): CandidateDetailsRecord {

        TODO("Not implemented yet")

    }

    @DeleteMapping("{id}")
    fun deleteCandidate(@PathVariable id: Long): ResponseEntity<Any> {

        TODO("Not implemented yet")

    }

}