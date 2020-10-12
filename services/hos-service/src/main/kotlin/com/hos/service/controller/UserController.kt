package com.hos.service.controller

import com.hos.service.model.dto.UserForm
import com.hos.service.model.entity.UserEntity
import com.hos.service.model.enum.QualifierType
import com.hos.service.model.enum.Resource
import com.hos.service.model.exception.ResourceNotFoundException
import com.hos.service.model.record.UserBasicsRecord
import com.hos.service.model.record.UserDetailsRecord
import com.hos.service.repo.UserRepository
import com.hos.service.service.UserService
import org.springframework.web.bind.annotation.*
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("user")
class UserController(val userService: UserService) {

    @GetMapping
    @RolesAllowed("ADMIN")
    fun userInfo(): List<UserBasicsRecord> {
        return userService.findAllUsers()
    }

    @GetMapping("/{id}")
    fun userInfo(@PathVariable id: Long): UserDetailsRecord {
        return userService.findUserById(id) ?: throw ResourceNotFoundException(Resource.USER, QualifierType.ID, "$id")
    }

    @PostMapping
    fun addUser(@RequestBody body: UserForm): UserDetailsRecord {

        return UserDetailsRecord(1, "", "", "", emptyList(), emptyList())
    }

    @PutMapping
    fun modifyUser(@RequestBody body: UserEntity): UserEntity {

        return UserEntity("login", "N/A", "password")
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): UserEntity {

        return UserEntity("login", "N/A", "password")
    }

}





