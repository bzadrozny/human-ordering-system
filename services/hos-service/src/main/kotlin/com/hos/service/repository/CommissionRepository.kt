package com.hos.service.repository

import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.enum.CommissionStatus
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CommissionRepository : JpaRepository<CommissionEntity, Long>, JpaSpecificationExecutor<CommissionEntity> {

    @Query("""
        select c from COMMISSION c 
        join fetch LOCATION l on c.location = l
        join fetch ORGANISATION o on l.organisation = o
        where (:status is null or c.status = :status) 
            and (:location is null or l.id = :location) 
            and (:organisation is null or o.id = :organisation) 
    """)
    fun findAllFiltered(status: CommissionStatus?, location: Long?, organisation: Long?, pageable: Pageable?): List<CommissionEntity>

}

