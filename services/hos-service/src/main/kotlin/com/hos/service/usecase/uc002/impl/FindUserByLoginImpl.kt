package com.hos.service.usecase.uc002.impl

import com.hos.service.model.record.UserDetailsRecord
import com.hos.service.repo.UserRepository
import com.hos.service.usecase.uc002.FindUserByLogin
import org.springframework.stereotype.Component

@Component
class FindUserByLoginImpl(
        val userRepository: UserRepository
) : FindUserByLogin {

    override fun findUserByLogin(login: String): UserDetailsRecord? {
        return userRepository.findByLogin(login)?.let {
            it.mapToUserDetailsRecord()
        }
    }

}