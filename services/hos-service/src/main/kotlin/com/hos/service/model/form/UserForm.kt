package com.hos.service.model.form

import com.hos.service.model.enum.Authority
import com.hos.service.model.enum.EntityStatus

class UserForm {

    val id: Long? = null
    val login: String? = null
    val email: String? = null
    val password: String? = null
    val status: EntityStatus? = null

    val name: String? = null
    val surname: String? = null
    val phone1: String? = null
    val phone2: String? = null

    val superior: Long? = null

    val organisation: Long? = null
    val department: Long? = null
    val location: Long? = null

    val authorities: List<Authority>? = null

    val acceptWarning: Boolean = false
}