package com.hos.service.repository

import com.hos.service.model.entity.DepartmentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DepartmentRepository : JpaRepository<DepartmentEntity, Long>