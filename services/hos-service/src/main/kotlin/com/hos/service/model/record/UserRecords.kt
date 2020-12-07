package com.hos.service.model.record

import com.hos.service.model.enum.Authority
import com.hos.service.model.enum.EntityStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserBasicRecord(
        val id: Long,
        val login: String,
        val email: String,
        val status: EntityStatus
)

data class UserDetailsRecord(
        val id: Long,
        val organisation: OrganisationBasicRecord,
        val version: Int,
        val login: String,
        val email: String,
        val password: String,
        val status: EntityStatus,
        val devices: Set<UserDeviceRecord>,
        val authorities: Set<Authority>
) {
    fun grantedAuthorities(): List<GrantedAuthority> {
        return authorities.map { auth -> SimpleGrantedAuthority(auth.name) }
    }

    fun comaSeparatedAuthorities(): String {
        return if (authorities.isEmpty()) {
            Authority.ANONYMOUS.name
        } else {
            authorities.map { it.name }.reduce { acc: String, authority: String -> "$authority, $acc" }
        }
    }
}

data class UserDeviceRecord(
        val id: Long,
        val version: Int,
        val agentType: String,
        val ipAddress: String
)