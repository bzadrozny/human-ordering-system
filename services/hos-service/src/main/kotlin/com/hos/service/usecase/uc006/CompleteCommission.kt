package com.hos.service.usecase.uc006

import com.hos.service.model.record.CommissionDetailsRecord

interface CompleteCommission {

    fun completeCommission(id: Long): CommissionDetailsRecord

}