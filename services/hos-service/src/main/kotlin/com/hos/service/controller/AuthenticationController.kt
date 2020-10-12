package com.hos.service.controller

import com.hos.service.model.dto.LoginForm
import com.hos.service.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@RestController
class AuthenticationController(
        private val userService: UserService
) {

    @PostMapping("/login")
    fun login(@RequestBody loginForm: LoginForm): ResponseEntity<String> {
        val jwtToken = userService.loginUser(loginForm.login, loginForm.password)
        return ResponseEntity.accepted()
                .header("Set-Cookie", "Authorization=Bearer $jwtToken")
                .body(jwtToken)
    }

    @PostMapping("/logout")
    fun logout(): ResponseEntity<JvmType.Object> {

        return ResponseEntity.noContent().header("test", "test").build()
    }


}





