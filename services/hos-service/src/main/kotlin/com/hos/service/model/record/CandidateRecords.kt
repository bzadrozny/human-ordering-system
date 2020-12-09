package com.hos.service.model.record

data class CandidateBasicRecord(
        val id: Long,
        val identificationNumber: String,
        val name: String,
        val surname: String,
        val phone: String
)

data class CandidateDetailsRecord(
        val id: Long,
        val name: String,
        val surname: String,
        val phone: String,
        val email: String,
        val identificationNumber: String,
        val contracts: List<ContractBasicRecord> = mutableListOf()
)
