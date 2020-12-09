package com.hos.service.controller

import com.hos.service.model.form.CandidateForm
import com.hos.service.model.form.CandidatesFilterForm
import com.hos.service.model.record.CandidateBasicRecord
import com.hos.service.model.record.CandidateDetailsRecord
import com.hos.service.model.record.CommissionDetailsRecord
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("candidate")
class CandidateController {

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RECRUITER', 'CLIENT')")
    fun filteredCandidates(@RequestParam filter: CandidatesFilterForm): List<CandidateBasicRecord> {

        TODO("Not implemented yet")

    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RECRUITER', 'CLIENT')")
    fun candidateDetails(@PathVariable id: Long): CandidateDetailsRecord {

        TODO("Not implemented yet")

    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RECRUITER')")
    fun addCandidate(@RequestBody body: CandidateForm): CommissionDetailsRecord {

        TODO("Not implemented yet")

    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RECRUITER')")
    fun modifyCandidate(@PathVariable id: Long, @RequestBody body: CandidateForm): CandidateDetailsRecord {

        TODO("Not implemented yet")

    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    fun deleteCandidate(@PathVariable id: Long): ResponseEntity<Any> {

        TODO("Not implemented yet")

    }

}