package com.hos.service.service

import com.hos.service.model.form.UserForm
import com.hos.service.model.record.UserBasicRecord
import com.hos.service.model.record.UserDetailsRecord

interface UserService {

    fun loginUser(login: String?, password: String?): String?

    fun findAllUsers(): List<UserBasicRecord>
    fun findAllManagers(): List<UserBasicRecord>
    fun findUserById(id: Long): UserDetailsRecord?

    fun registerUser(userForm: UserForm): UserDetailsRecord?
    fun modifyUser(userForm: UserForm): UserDetailsRecord?

}