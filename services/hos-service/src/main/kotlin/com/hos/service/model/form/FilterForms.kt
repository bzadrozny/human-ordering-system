package com.hos.service.model.form

import com.hos.service.model.enum.CommissionStatus
import org.springframework.web.bind.annotation.RequestParam

class CommissionFilterForm(
        @RequestParam(required = false) val id: Long? = null,
        @RequestParam(required = false) val organisation: Long? = null,
        @RequestParam(required = false) val location: Long? = null,
        @RequestParam(required = false) val status: CommissionStatus? = null
)

class CandidatesFilterForm(
        @RequestParam(required = false) val identificationNumber: String,
        @RequestParam(required = false) val name: String,
        @RequestParam(required = false) val surname: String
)