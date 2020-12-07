package com.hos.service.model.form

class CandidateForm(
        var id: Long? = null,
        var name: String? = null,
        var surname: String? = null,
        var phone: String? = null,
        var email: String? = null,
        var approvment: Boolean? = null,
        var address: AddressForm? = null
)