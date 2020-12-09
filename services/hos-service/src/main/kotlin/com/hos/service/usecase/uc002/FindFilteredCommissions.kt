package com.hos.service.usecase.uc002

import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.form.CommissionFilterForm
import com.hos.service.model.record.CommissionBasicRecord

interface FindFilteredCommissions {

    fun findFilteredCommissions(filter: CommissionFilterForm): List<CommissionEntity>

}