package com.hos.service.service.impl

import com.hos.service.model.record.UserBasicsRecord
import com.hos.service.model.record.UserDetailsRecord
import com.hos.service.repo.UserRepository
import com.hos.service.service.UserService
import com.hos.service.usecase.uc001.LoginUser
import com.hos.service.usecase.uc002.FindUserByLogin
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
        val uc001: LoginUser,
        val uc002: FindUserByLogin,
        val userRepository: UserRepository
) : UserService {

    override fun findAllUsers(): List<UserBasicsRecord> {
        return userRepository.findAll()
                .map { it.mapToUserBasicsRecord() }
    }

    override fun findUserById(id: Long): UserDetailsRecord? {
        return userRepository.findById(id)
                .map { it.mapToUserDetailsRecord() }
                .orElse(null)
    }

    override fun findUserByLogin(login: String): UserDetailsRecord? {
        return uc002.findUserByLogin(login)
    }

    override fun loginUser(login: String?, password: String?): String? {
        return uc001.login(login, password)
    }

}