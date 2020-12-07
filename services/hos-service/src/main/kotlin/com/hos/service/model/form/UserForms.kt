package com.hos.service.model.form

import com.hos.service.model.enum.Authority
import com.hos.service.model.enum.EntityStatus
import net.minidev.json.annotate.JsonIgnore

class UserForm {

    val id: Long? = null
    val login: String? = null
    val password: String? = null
    val status: EntityStatus? = null
    val personal: UserPersonalForm? = null
    val administrative: UserAdministrativeForm? = null
    val acceptWarning: Boolean = false

}

class UserPersonalForm {

    val name: String? = null
    val surname: String? = null
    val phone1: String? = null
    val phone2: String? = null
    val email: String? = null

}

class UserAdministrativeForm {

    val superior: Long? = null
    val organisation: Long? = null
    val department: Long? = null
    val location: Long? = null
    val authorities: List<Authority>? = null

}
