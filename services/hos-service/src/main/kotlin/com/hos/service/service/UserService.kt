package com.hos.service.service

import com.hos.service.model.record.UserBasicsRecord
import com.hos.service.model.record.UserDetailsRecord

interface UserService {

    fun findAllUsers(): List<UserBasicsRecord>
    fun findUserById(id: Long): UserDetailsRecord?
    fun findUserByLogin(login: String): UserDetailsRecord?
    fun loginUser(login: String?, password: String?): String?

}