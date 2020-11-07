package com.hos.service.repo

import com.hos.service.model.entity.OrganisationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrganisationRepository : JpaRepository<OrganisationEntity, Long>