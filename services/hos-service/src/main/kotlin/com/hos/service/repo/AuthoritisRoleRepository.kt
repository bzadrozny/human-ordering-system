package com.hos.service.repo

import com.hos.service.model.entity.AuthoritisRoleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthoritisRoleRepository : JpaRepository<AuthoritisRoleEntity, Long>