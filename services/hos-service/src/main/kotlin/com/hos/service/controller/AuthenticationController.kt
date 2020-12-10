package com.hos.service.controller

import com.hos.service.model.form.LoginForm
import com.hos.service.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AuthenticationController(
        private val userService: UserService
) {

    @PostMapping("/login")
    fun login(@RequestBody loginForm: LoginForm): String {
        val jwtToken = userService.loginUser(loginForm.login, loginForm.password)
        return jwtToken!!
    }

    @PostMapping("/logout")
    fun logout(): ResponseEntity<Any> {
        return ResponseEntity.noContent().header("test", "test").build()
    }

}
