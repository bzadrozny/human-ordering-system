package com.hos.service.repo

import com.hos.service.model.entity.DepartmentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DepartmentRepository : JpaRepository<DepartmentEntity, Long>