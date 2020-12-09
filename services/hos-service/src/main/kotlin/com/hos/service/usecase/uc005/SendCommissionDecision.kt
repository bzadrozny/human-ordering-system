package com.hos.service.usecase.uc005

import com.hos.service.model.form.CommissionDecisionForm
import com.hos.service.model.record.CommissionDetailsRecord

interface SendCommissionDecision {

    fun sendCommissionDecision(form: CommissionDecisionForm): CommissionDetailsRecord

}