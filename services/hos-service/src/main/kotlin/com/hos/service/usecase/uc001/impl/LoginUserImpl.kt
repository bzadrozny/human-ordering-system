package com.hos.service.usecase.uc001.impl

import com.hos.service.model.enum.QualifierType
import com.hos.service.model.enum.Resource
import com.hos.service.model.exception.ResourceNotFoundException
import com.hos.service.repo.UserRepository
import com.hos.service.security.JwtTokenUtils
import com.hos.service.usecase.uc001.LoginUser
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class LoginUserImpl(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val jwtTokenUtils: JwtTokenUtils
) : LoginUser {

    override fun login(login: String?, password: String?): String? {
        if (login == null || password == null) throw ResourceNotFoundException(Resource.USER, QualifierType.LOGIN, "$login")
        val user = userRepository.findByLogin(login).orElseThrow { ResourceNotFoundException(Resource.USER, QualifierType.LOGIN, "$login") }
        if (!passwordEncoder.matches(password, user.passwrod)) throw  ResourceNotFoundException(Resource.USER, QualifierType.LOGIN, "$login")
        return jwtTokenUtils.createToken(user.mapToUserDetailsRecord())
    }

}