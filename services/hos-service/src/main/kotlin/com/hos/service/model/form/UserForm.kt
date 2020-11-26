package com.hos.service.model.form

import com.hos.service.model.entity.AuthoritisRoleEntity
import com.hos.service.model.enum.AdministrationRole

class UserForm {
    val login: String? = null
    var email: String? = null
    val passwrod: String? = null
    val passwrodConfirmation: String? = null

    val administrationRole: AdministrationRole? = null
    val administrationUnit: AdministrationRole? = null
    val authorities: List<AuthoritisRoleEntity> = listOf()

}