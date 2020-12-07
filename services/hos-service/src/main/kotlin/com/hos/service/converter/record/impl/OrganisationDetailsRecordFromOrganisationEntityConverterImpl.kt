package com.hos.service.converter.record.impl

import com.hos.service.converter.Converter
import com.hos.service.converter.record.RecordConverter
import com.hos.service.model.entity.DepartmentEntity
import com.hos.service.model.entity.LocationEntity
import com.hos.service.model.entity.OrganisationEntity
import com.hos.service.model.record.DepartmentRecord
import com.hos.service.model.record.LocationRecord
import com.hos.service.model.record.OrganisationBasicRecord
import com.hos.service.model.record.OrganisationDetailsRecord
import org.springframework.stereotype.Component

@Component
class OrganisationDetailsRecordFromOrganisationEntityConverterImpl(
        private val departmentConverter: Converter<DepartmentEntity, DepartmentRecord>,
        private val locationConverter: Converter<LocationEntity, LocationRecord>
) : RecordConverter<OrganisationEntity, OrganisationDetailsRecord>() {

    override fun create(source: OrganisationEntity): OrganisationDetailsRecord {
        return OrganisationDetailsRecord(
                id = source.id,
                name = source.name,
                nip = source.nip,
                departments = source.departments.map(departmentConverter::create),
                locations = source.locations.map(locationConverter::create),
                status = source.status
        )
    }

}