package com.hos.service.usecase.uc004

import com.hos.service.model.record.CommissionDetailsRecord

interface SendCommission {

    fun sendCommission(id: Long): CommissionDetailsRecord

}