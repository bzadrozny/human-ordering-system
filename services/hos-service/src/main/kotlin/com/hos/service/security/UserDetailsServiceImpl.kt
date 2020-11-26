package com.hos.service.security

import com.hos.service.model.enum.Authority
import com.hos.service.model.record.UserDetailsRecord
import com.hos.service.usecase.uc002.FindUserByLogin
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import javax.transaction.Transactional


@Component
class UserDetailsServiceImpl(
        private val uc002: FindUserByLogin,
) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String?): UserDetails {
        val result: UserDetailsRecord? = username?.let { uc002.findUserByLogin(it) }
        return result?.let {
            UserDetailsContainer(
                    it.id,
                    it.login,
                    it.password,
                    it.grantedAuthorities()
            )
        } ?: {
            UserDetailsContainer(
                    username = username,
                    authorities = AuthorityUtils.createAuthorityList(Authority.ANONYMOUS.name)
            )
        }()
    }

}
