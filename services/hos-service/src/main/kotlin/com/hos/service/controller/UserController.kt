package com.hos.service.controller

import com.hos.service.model.form.UserForm
import com.hos.service.model.entity.UserEntity
import com.hos.service.model.enum.QualifierType
import com.hos.service.model.enum.Resource
import com.hos.service.model.exception.ResourceNotFoundException
import com.hos.service.model.record.UserBasicsRecord
import com.hos.service.model.record.UserDetailsRecord
import com.hos.service.service.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("user")
class UserController(private val userService: UserService) {

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    fun allUsers(): List<UserBasicsRecord> {
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

    @PutMapping
    fun modifyUser(@RequestBody body: UserForm): UserDetailsRecord? {
        return userService.modifyUser(body)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun deleteUser(@PathVariable id: Long): Boolean {

        //TODO do wymiany

        return false
    }

}





