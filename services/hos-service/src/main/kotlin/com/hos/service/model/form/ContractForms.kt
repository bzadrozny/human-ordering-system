package com.hos.service.model.form

import com.hos.service.model.enum.ContractType
import java.time.LocalDate

class ContractForm(
        var id: Long? = null,
        var commission: Long? = null,
        var commissionRecord: Long? = null,
        var code: String? = null,
        var contractDate: LocalDate? = null,
        var startDate: LocalDate? = null,
        var endDate: LocalDate? = null,
        var salary: Float? = null,
        var contractType: ContractType? = null,
        var description: String? = null,
        var candidate: Long? = null,
        var recruiter: Long? = null
)