package com.hos.service.repo

import com.hos.service.model.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {

    fun findByLogin(login: String): UserEntity?
    fun findByEmail(email: String): UserEntity?
    fun findByLoginOrEmail(login: String, email: String): UserEntity?

}