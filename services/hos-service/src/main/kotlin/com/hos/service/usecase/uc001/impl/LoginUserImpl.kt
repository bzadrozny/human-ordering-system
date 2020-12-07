package com.hos.service.usecase.uc001.impl

import com.hos.service.converter.Converter
import com.hos.service.model.entity.UserEntity
import com.hos.service.model.enum.QualifierType
import com.hos.service.model.enum.Resource
import com.hos.service.model.exception.ResourceNotFoundException
import com.hos.service.model.record.UserDetailsRecord
import com.hos.service.repo.UserRepository
import com.hos.service.security.JwtTokenUtils
import com.hos.service.usecase.uc001.LoginUser
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class LoginUserImpl(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val jwtTokenUtils: JwtTokenUtils,
        private val userDetailsConverter: Converter<UserEntity, UserDetailsRecord>
) : LoginUser {

    override fun login(login: String?, password: String?): String? {
        if (login != null && password != null) {
            userRepository.findByLogin(login)?.let {
                if (passwordEncoder.matches(password, it.password)) {
                    return jwtTokenUtils.createToken(userDetailsConverter.create(it))
                }
            }
        }
        throw ResourceNotFoundException(Resource.USER, QualifierType.LOGIN, "$login")
    }

}