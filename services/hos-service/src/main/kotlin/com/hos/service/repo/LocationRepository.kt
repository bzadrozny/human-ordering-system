package com.hos.service.repo

import com.hos.service.model.entity.LocationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository : JpaRepository<LocationEntity, Long>