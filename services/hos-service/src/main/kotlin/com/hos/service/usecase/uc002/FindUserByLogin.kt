package com.hos.service.usecase.uc002

import com.hos.service.model.record.UserDetailsRecord

interface FindUserByLogin {

    fun findUserByLogin(login: String): UserDetailsRecord?

}