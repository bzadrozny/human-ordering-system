package com.hos.service.usecase.uc002.impl

import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.form.CommissionFilterForm
import com.hos.service.repository.CommissionRepository
import com.hos.service.usecase.uc002.FindFilteredCommissions
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class FindFilteredCommissionsImpl(
        private val repository: CommissionRepository
) : FindFilteredCommissions {

    override fun findFilteredCommissions(filter: CommissionFilterForm): List<CommissionEntity> {
        return if (filter.id != null) repository.findByIdOrNull(filter.id)
                ?.let { listOf(it) }
                ?: emptyList()
        else repository.findAllFiltered(
                filter.status,
                filter.location,
                filter.organisation
        )
    }

}