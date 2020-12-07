package com.hos.service.model.form

import com.hos.service.model.enum.CommissionStatus

class CommissionFilterForm(
        val id: Long? = null,
        val organisation: Long? = null,
        val location: Long? = null,
        val status: CommissionStatus? = null
)

class CandidatesFilterForm(
        val id: Long? = null,
        val identificationNumber: String,
        val name: String,
        val surname: String
)