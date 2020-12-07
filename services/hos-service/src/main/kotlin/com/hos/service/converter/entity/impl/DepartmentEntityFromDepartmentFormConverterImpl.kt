package com.hos.service.converter.entity.impl

import com.hos.service.converter.Converter
import com.hos.service.converter.MultiConverter
import com.hos.service.model.entity.DepartmentEntity
import com.hos.service.model.entity.OrganisationEntity
import com.hos.service.model.form.DepartmentForm
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class DepartmentEntityFromDepartmentFormConverterImpl : MultiConverter<DepartmentForm, OrganisationEntity, DepartmentEntity> {

    override fun create(source1: DepartmentForm, source2: OrganisationEntity): DepartmentEntity {
        return DepartmentEntity(
                name = source1.name!!,
                status = source1.status!!,
                organisation = source2
        )
    }

    override fun merge(source1: DepartmentForm, source2: OrganisationEntity, target: DepartmentEntity): DepartmentEntity {
        source1.name?.let { target.name = it }
        source1.status?.let { target.status = it }
        return target
    }

}