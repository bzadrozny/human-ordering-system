package com.hos.service.security

import com.hos.service.model.record.UserDetailsRecord
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.*
import java.util.function.Function

@Component
@PropertySource(value = ["classpath:security/security.properties"])
class JwtTokenUtils : Serializable {

    @Value("\${jwt.secret.key}")
    private val secret: String? = null

    @Value("\${jwt.expiration.max}")
    private val jwtExpiration: Long = 0
        get() = field * 1000 * 60

    fun getSubjectFromToken(token: String): String {
        return getClaimFromToken(token) { obj: Claims -> obj.subject }
    }

    private fun getIssuedAtDateFromToken(token: String): Date {
        return getClaimFromToken(token) { obj: Claims -> obj.issuedAt }
    }

    private fun getExpirationDateFromToken(token: String): Date {
        return getClaimFromToken(token) { obj: Claims -> obj.expiration }
    }

    private fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .body
        return claimsResolver.apply(claims)
    }

    fun getAuthorities(jwtToken: String?): List<GrantedAuthority> {
        val claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwtToken)
                .body
        val authorities = claims.get("authorities", String::class.java)
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities)
    }

    fun createToken(user: UserDetailsRecord): String {
        val now = Date()
        return Jwts.builder()
                .setSubject(user.login)
                .setIssuedAt(now)
                .setExpiration(Date(now.time + jwtExpiration))
                .claim("authorities", user.comaSeparatedAuthorities())
                .signWith(SignatureAlgorithm.HS384, secret)
                .compact()
    }

    fun validateToken(token: String, username: String?): Boolean {
        val tokenSubject = getSubjectFromToken(token)
        val tokenIssuedDate = getIssuedAtDateFromToken(token)
        val tokenExpirationDate = getExpirationDateFromToken(token)
        return (StringUtils.equals(tokenSubject, username)
                && tokenExpirationDate.after(Date())
                && tokenExpirationDate.time - tokenIssuedDate.time <= jwtExpiration)
    }

}