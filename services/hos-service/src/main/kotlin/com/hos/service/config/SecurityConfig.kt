package com.hos.service.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    val publicURL: Array<String> = arrayOf(
            "/login",
            "/test"
    )

    override fun configure(http: HttpSecurity?) {
        http?.csrf()?.disable()
                ?.httpBasic()?.disable()
                ?.logout()?.disable()
                ?.formLogin()?.disable()

        http?.authorizeRequests()?.antMatchers(*publicURL)?.permitAll()
    }
}