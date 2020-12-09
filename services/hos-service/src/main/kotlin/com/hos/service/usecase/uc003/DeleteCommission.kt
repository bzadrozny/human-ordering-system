package com.hos.service.usecase.uc003

import com.hos.service.model.record.CommissionDetailsRecord

interface DeleteCommission {

    fun deleteCommission(id: Long): CommissionDetailsRecord

}