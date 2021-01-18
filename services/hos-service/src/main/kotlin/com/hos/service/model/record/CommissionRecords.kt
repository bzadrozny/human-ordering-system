package com.hos.service.model.record

import com.hos.service.model.enum.CommissionRecordStatus
import com.hos.service.model.enum.CommissionStatus
import com.hos.service.model.enum.SettlementType
import java.time.LocalDate

data class CommissionBasicRecord(
    val id: Long,
    val orderDate: LocalDate?,
    val status: CommissionStatus,
    val organisation: String,
    val location: String,
    val threaten: Boolean,
    val organisationId: Long
)

data class CommissionDetailsRecord(
    val id: Long,
    val status: CommissionStatus,
    val orderDate: LocalDate?,
    val decisionDate: LocalDate?,
    val decisionDescription: String?,
    val realisationDate: LocalDate?,
    val completedDate: LocalDate?,
    val principal: UserBasicRecord,
    val location: LocationRecord,
    val executor: UserBasicRecord?,
    val records: List<CommissionRecordDetailsRecord> = mutableListOf(),
    var description: String?,
)

data class CommissionRecordDetailsRecord(
    val id: Long,
    val status: CommissionRecordStatus,
    val jobName: String,
    val ordered: Int,
    val acceptedOrdered: Int?,
    val recruited: Int,
    val startDate: LocalDate,
    val acceptedStartDate: LocalDate?,
    val endDate: LocalDate?,
    val wageRateMin: Float,
    val wageRateMax: Float,
    val settlementType: SettlementType,
    val contracts: List<ContractDetailsRecord> = mutableListOf(),
    val description: String?
)