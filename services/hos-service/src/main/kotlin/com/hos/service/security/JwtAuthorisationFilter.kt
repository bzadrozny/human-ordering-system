package com.hos.service.security

import com.hos.service.utils.loggerFor
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
class JwtAuthorisationFilter : OncePerRequestFilter() {

    @Autowired
    private val jwtTokenUtils: JwtTokenUtils = JwtTokenUtils()
    private val log = loggerFor(javaClass)

    override fun doFilterInternal(req: HttpServletRequest, resp: HttpServletResponse, chain: FilterChain) {
        val secContext = SecurityContextHolder.getContext();
        if (secContext.authentication != null) {
            chain.doFilter(req, resp)
            return
        }

        val requestTokenHeader = req.getHeader("Authorization") ?: req.getHeader("authorization")
        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            log.warn("For request: ${req.requestURI}")
            log.warn("JWT Token does not begin with Bearer String | Header: $requestTokenHeader")
            chain.doFilter(req, resp)
            return
        }

        val jwtToken = requestTokenHeader.removePrefix("Bearer ")
        val jwtSubject = getSubject(jwtToken)
        if (StringUtils.isEmpty(jwtSubject) || !jwtTokenUtils.validateToken(jwtToken, jwtSubject)) {
            chain.doFilter(req, resp)
            return
        }

        val authorities = jwtTokenUtils.getAuthorities(jwtToken)
        if (authorities.isEmpty()) {
            chain.doFilter(req, resp)
            return
        }

        secContext.authentication = createAuthentication(jwtSubject, jwtToken, authorities, req)
        chain.doFilter(req, resp)
    }

    private fun createAuthentication(jwtSubject: String?, jwtToken: String, authorities: List<GrantedAuthority>, req: HttpServletRequest): Authentication? {
        val auth = UsernamePasswordAuthenticationToken(jwtSubject, jwtToken, authorities)
        auth.details = WebAuthenticationDetailsSource().buildDetails(req)
        return auth
    }

    private fun getSubject(jwtToken: String): String? {
        try {
            return jwtTokenUtils.getSubjectFromToken(jwtToken)
        } catch (exception: ExpiredJwtException) {
            log.warn("Request to parse expired JWT : {} failed : {}", jwtToken, exception.message)
        } catch (exception: UnsupportedJwtException) {
            log.warn("Request to parse unsupported JWT : {} failed : {}", jwtToken, exception.message)
        } catch (exception: MalformedJwtException) {
            log.warn("Request to parse invalid JWT : {} failed : {}", jwtToken, exception.message)
        } catch (exception: SignatureException) {
            log.warn("Request to parse JWT with invalid signature : {} failed : {}", jwtToken, exception.message)
        } catch (exception: IllegalArgumentException) {
            log.warn("Request to parse empty or null JWT : {} failed : {}", jwtToken, exception.message)
        }
        return null
    }
}