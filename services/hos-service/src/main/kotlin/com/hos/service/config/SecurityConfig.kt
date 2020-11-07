package com.hos.service.config

import com.hos.service.security.JwtAuthorisationFilter
import com.hos.service.security.UserDetailsServiceImpl
import com.hos.service.service.UserService
import com.hos.service.usecase.uc002.FindUserByLogin
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@PropertySource(value = ["classpath:security/security.properties"])
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfig(
        private val uc002: FindUserByLogin
) : WebSecurityConfigurerAdapter() {

    val publicURL: Array<String> = arrayOf(
            "/login"
    )

    override fun configure(http: HttpSecurity?) {
        http
                ?.csrf()?.disable()
                ?.httpBasic()?.disable()
                ?.formLogin()?.disable()
                ?.logout()?.disable()
                ?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http
                ?.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter::class.java)

        http
                ?.authorizeRequests()?.antMatchers(*publicURL)?.permitAll()?.and()
                ?.authorizeRequests()?.anyRequest()?.authenticated()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailsServiceBean())
                ?.passwordEncoder(passwordEncoder())
    }

    @Bean
    override fun userDetailsServiceBean(): UserDetailsService {
        return UserDetailsServiceImpl(uc002::findUserByLogin)
    }

    @Bean
    fun jwtAuthorizationFilter(): JwtAuthorisationFilter {
        return JwtAuthorisationFilter()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(BCryptVersion.`$2Y`, 18)
    }

}