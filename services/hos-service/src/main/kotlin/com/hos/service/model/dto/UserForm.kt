package com.hos.service.model.dto

import com.hos.service.model.entity.AuthoritisRoleEntity
import com.hos.service.model.enum.AdministrationUnit

class UserForm(
        val login: String,
        var email: String,
        val department: AdministrationUnit,

        val passwrod: String,
        val passwrodConfirmation: String,

        val authorities: List<AuthoritisRoleEntity>
)