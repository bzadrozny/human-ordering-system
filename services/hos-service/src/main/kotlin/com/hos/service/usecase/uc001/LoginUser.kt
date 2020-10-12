package com.hos.service.usecase.uc001

interface LoginUser {
    fun login(login: String?, password: String?): String?
}