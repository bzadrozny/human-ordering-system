package com.hos.service.model.record

import com.hos.service.model.enum.ContractType
import java.time.LocalDate

data class ContractBasicRecord(
        val id: Long,
        val code: String,
        val startDate: LocalDate
)

data class ContractDetailsRecord(
        val id: Long,
        val code: String,
        val contractDate: LocalDate,
        val startDate: LocalDate,
        val endDate: LocalDate?,
        val contractType: ContractType,
        val salary: Float,
        val recruiter: UserBasicRecord,
        val candidate: CandidateBasicRecord?,
        val description: String?,
        val approved: Boolean?
)