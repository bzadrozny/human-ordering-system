package com.hos.service.service

import com.hos.service.model.form.CommissionDecisionForm
import com.hos.service.model.form.CommissionFilterForm
import com.hos.service.model.form.CommissionForm
import com.hos.service.model.record.CommissionBasicRecord
import com.hos.service.model.record.CommissionDetailsRecord

interface CommissionService {

    fun findAllCommissions(filter: CommissionFilterForm): List<CommissionBasicRecord>
    fun findCommissionDetailsById(id: Long): CommissionDetailsRecord

    fun createCommission(form: CommissionForm): CommissionDetailsRecord
    fun modifyCommission(form: CommissionForm): CommissionDetailsRecord
    fun deleteCommission(id: Long): CommissionDetailsRecord

    fun sendCommission(id: Long): CommissionDetailsRecord
    fun sendCommissionDecision(form: CommissionDecisionForm): CommissionDetailsRecord
    fun completeCommission(id: Long): CommissionDetailsRecord

}