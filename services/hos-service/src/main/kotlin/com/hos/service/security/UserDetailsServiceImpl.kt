package com.hos.service.security

import com.hos.service.model.converter.Converter
import com.hos.service.model.entity.UserEntity
import com.hos.service.model.enum.Authority
import com.hos.service.model.record.UserDetailsRecord
import com.hos.service.repo.UserRepository
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import javax.transaction.Transactional


@Component
class UserDetailsServiceImpl(
        private val userRepository: UserRepository,
        private val userDetailsConverter: Converter<UserEntity, UserDetailsRecord>
) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String?): UserDetails {
        return username?.let { userRepository.findByLogin(it) }
                ?.let { userDetailsConverter.create(it) }
                ?.let {
                    UserDetailsContainer(
                            it.id,
                            it.organisation,
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
