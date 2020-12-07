package com.hos.service.converter.entity.impl

import com.hos.service.converter.Converter
import com.hos.service.converter.MultiConverter
import com.hos.service.model.entity.DepartmentEntity
import com.hos.service.model.entity.LocationEntity
import com.hos.service.model.entity.OrganisationEntity
import com.hos.service.model.enum.EntityStatus
import com.hos.service.model.form.DepartmentForm
import com.hos.service.model.form.LocationForm
import com.hos.service.model.form.OrganisationForm
import org.springframework.stereotype.Component

@Component
class OrganisationEntityFromOrganisationFormConverterImpl(
        private val departmentConverter: MultiConverter<DepartmentForm, OrganisationEntity, DepartmentEntity>,
        private val locationConverter: MultiConverter<LocationForm, OrganisationEntity, LocationEntity>
) : Converter<OrganisationForm, OrganisationEntity> {

    override fun create(source: OrganisationForm): OrganisationEntity {
        val target = OrganisationEntity(
                name = source.name!!,
                nip = source.nip!!,
                status = source.status!!
        )
        source.departments?.forEach {
            target.departments.add(departmentConverter.create(it, target))
        }
        source.locations?.forEach {
            target.locations.add(locationConverter.create(it, target))
        }
        return target
    }

    override fun merge(source: OrganisationForm, target: OrganisationEntity): OrganisationEntity {
        source.name?.let { target.name = it }
        source.nip?.let { target.nip = it }
        source.status?.let { target.status = it }

        source.departments?.let { departmentForms ->
            target.departments
                    .filter { td -> !departmentForms.any { sd -> sd.id != null && sd.id == td.id } }
                    .forEach { td -> td.status = EntityStatus.DELETED }

            departmentForms.filter { it.id == null }
                    .forEach { target.departments.add(departmentConverter.create(it, target)) }

            departmentForms.filter { it.id != null }.forEach { form ->
                target.departments.filter { it.id == form.id }.forEach { entity ->
                    departmentConverter.merge(form, target, entity)
                }
            }
        }

        source.locations?.let { locationForms ->
            target.locations
                    .filter { td -> !locationForms.any { sd -> sd.id != null && sd.id == td.id } }
                    .forEach { td -> td.status = EntityStatus.DELETED }

            locationForms.filter { it.id == null }
                    .forEach { target.locations.add(locationConverter.create(it, target)) }

            locationForms.filter { it.id != null }.forEach { form ->
                target.locations.filter { it.id == form.id }.forEach { entity ->
                    locationConverter.merge(form, target, entity)
                }
            }
        }

        return target
    }

}