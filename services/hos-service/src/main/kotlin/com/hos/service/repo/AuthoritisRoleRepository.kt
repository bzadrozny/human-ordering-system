package com.hos.service.repo

import com.hos.service.model.entity.AuthorityRoleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthoritisRoleRepository : JpaRepository<AuthorityRoleEntity, Long>