package com.hos.service.repository

import com.hos.service.model.entity.CommissionRecordEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommissionRecordRepository : JpaRepository<CommissionRecordEntity, Long>