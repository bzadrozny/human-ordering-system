package com.hos.service.security

import com.hos.service.model.enum.Authority
import com.hos.service.model.record.UserDetailsRecord
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import javax.transaction.Transactional


open class UserDetailsServiceImpl(
        private val findUserByLogin: (login: String) -> UserDetailsRecord?
) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String?): UserDetails {
        val result: UserDetailsRecord? = username?.let { findUserByLogin(it) }
        return result?.let {
            User.withUsername(it.login).password(it.password).authorities(it.grantedAuthorities()).build()
        } ?: {
            User.withUsername(username).authorities(Authority.ANONYMOUS.name).build()
        }()
    }

}
