package com.hos.service.model.form

import com.hos.service.model.enum.*
import java.time.LocalDate

class CommissionForm(
    var id: Long? = null,
    var principal: Long? = null,
    var location: Long? = null,
    var description: String? = null,
    var status: CommissionStatus? = null,
    var records: Set<CommissionRecordForm>? = null
)

class CommissionRecordForm(
    var id: Long? = null,
    var commission: Long? = null,
    var jobName: String? = null,
    var ordered: Int? = null,
    var startDate: LocalDate? = null,
    var endDate: LocalDate? = null,
    var wageRateMin: Float? = null,
    var wageRateMax: Float? = null,
    var settlementType: SettlementType? = null,
    var description: String? = null,
    var status: CommissionRecordStatus? = null
)

class CommissionDecisionForm(
    var id: Long? = null,
    var decision: CommissionDecision? = null,
    var executor: Long? = null,
    var realisationDate: LocalDate? = null,
    var records: Set<CommissionRecordDecisionForm>? = null,
    var description: String? = null
)

class CommissionRecordDecisionForm(
    var id: Long? = null,
    var decision: CommissionDecision? = null,
    var accepted: Int? = null,
    var startDate: LocalDate? = null
)
