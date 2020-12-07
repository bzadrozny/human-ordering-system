package com.hos.service.converter.record.impl

import com.hos.service.converter.record.RecordConverter
import com.hos.service.model.entity.OrganisationEntity
import com.hos.service.model.record.OrganisationBasicRecord
import org.springframework.stereotype.Component

@Component
class OrganisationBasicRecordFromOrganisationEntityConverterImpl : RecordConverter<OrganisationEntity, OrganisationBasicRecord>() {

    override fun create(source: OrganisationEntity): OrganisationBasicRecord {
        return OrganisationBasicRecord(
                id = source.id,
                name = source.name,
                nip = source.nip,
                status = source.status
        )
    }

}