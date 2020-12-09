package com.hos.service.repository

import com.hos.service.model.entity.ContractEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContractRepository : JpaRepository<ContractEntity, Long>