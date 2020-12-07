package com.hos.service.controller

import com.hos.service.model.form.UserForm
import com.hos.service.model.record.UserBasicRecord
import com.hos.service.model.record.UserDetailsRecord
import com.hos.service.service.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class UserController(private val userService: UserService) {

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    fun allUsers(): List<UserBasicRecord> {
        return userService.findAllUsers()
    }

    @GetMapping("/{id}")
    fun userDetails(@PathVariable id: Long): UserDetailsRecord? {
        return userService.findUserById(id)
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTOR', 'MANAGER')")
    fun registerUser(@RequestBody body: UserForm): UserDetailsRecord? {
        return userService.registerUser(body)
    }

    @PutMapping("{id}")
    fun modifyUser(@PathVariable id: Long, @RequestBody body: UserForm): UserDetailsRecord? {
        return userService.modifyUser(body)
    }

}