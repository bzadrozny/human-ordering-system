package com.hos.service.converter.record.impl

import com.hos.service.converter.record.RecordConverter
import com.hos.service.model.entity.CandidateEntity
import com.hos.service.model.record.CandidateBasicRecord
import org.springframework.stereotype.Component

@Component
class CandidateBasicRecordFromCandidateEntityConverterImpl : RecordConverter<CandidateEntity, CandidateBasicRecord>() {

    override fun create(source: CandidateEntity): CandidateBasicRecord {
        return CandidateBasicRecord(
                id = source.id,
                identificationNumber = source.identificationNumber,
                name = source.name,
                surname = source.surname,
                phone = source.phone
        )
    }

}