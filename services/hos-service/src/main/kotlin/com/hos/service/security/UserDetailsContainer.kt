package com.hos.service.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class UserDetailsContainer(
        val id: Long? = null,
        username: String?,
        password: String? = null,
        authorities: List<GrantedAuthority>?
) : User(username, password, authorities)