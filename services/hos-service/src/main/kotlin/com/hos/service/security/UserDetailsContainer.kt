package com.hos.service.security

import com.hos.service.model.enum.Authority
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class UserDetailsContainer(
        val id: Long? = null,
        val organisation: Long? = null,
        username: String?,
        password: String? = "N/A",
        authorities: List<GrantedAuthority>?
) : User(username, password, authorities) {

    fun isAdmin(): Boolean = authorities.any { it.authority == Authority.ADMIN.name }

    fun isDirector(): Boolean = authorities.any { it.authority == Authority.DIRECTOR.name }

    fun isManager(): Boolean = authorities.any { it.authority == Authority.MANAGER.name }

    fun isClient(): Boolean = authorities.any { it.authority == Authority.CLIENT.name }

    fun isRecruiter(): Boolean = authorities.any { it.authority == Authority.RECRUITER.name }

}
