package com.hos.service.config

import com.hos.service.security.JwtAuthorisationFilter
import com.hos.service.security.UserDetailsServiceImpl
//import com.hos.service.security.UserDetailsServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import javax.servlet.http.HttpServletResponse


@Configuration
@PropertySource(value = ["classpath:security/security.properties"])
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfig(
        private val userDetailsService: UserDetailsServiceImpl
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http?.sessionManagement()
                ?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ?.and()

                ?.exceptionHandling()
                ?.authenticationEntryPoint { _, response, authException ->
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.message)
                }
                ?.and()

                ?.cors()?.and()
                ?.csrf()?.disable()
                ?.httpBasic()?.disable()
                ?.formLogin()?.disable()
                ?.logout()?.disable()

                ?.authorizeRequests()
                ?.antMatchers("/login")?.anonymous()
                ?.anyRequest()?.authenticated()
                ?.and()

                ?.addFilterBefore(
                        jwtAuthorizationFilter(),
                        UsernamePasswordAuthenticationFilter::class.java
                )
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return source
    }

    @Bean
    fun jwtAuthorizationFilter(): JwtAuthorisationFilter {
        return JwtAuthorisationFilter(userDetailsService)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}