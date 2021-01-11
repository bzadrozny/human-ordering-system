package com.hos.service.repository

import com.hos.service.model.entity.UserEntity
import com.hos.service.model.enum.Authority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {

    @Query(
        """select u 
            from AuthorityRoleEntity a 
            join UserEntity u on a.user = u
            where a.role = ?1
            group by u"""
    )
    fun findAllWithAuthority(authority: Authority): List<UserEntity>

    fun findByLogin(login: String): UserEntity?

    fun findByLoginOrEmail(login: String, email: String): UserEntity?

}