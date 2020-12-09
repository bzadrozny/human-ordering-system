package com.hos.service.service

import com.hos.service.model.form.CommissionRecordForm
import com.hos.service.model.record.CommissionRecordDetailsRecord

interface CommissionRecordService {

    fun addRecord(form: CommissionRecordForm): CommissionRecordDetailsRecord
    fun modifyRecord(form: CommissionRecordForm): CommissionRecordDetailsRecord
    fun cancelRecord(commissionId: Long, commissionRecordId: Long): Boolean

}