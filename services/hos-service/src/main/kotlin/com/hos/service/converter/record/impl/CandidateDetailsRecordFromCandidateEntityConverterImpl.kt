package com.hos.service.converter.record.impl

import com.hos.service.converter.Converter
import com.hos.service.converter.record.RecordConverter
import com.hos.service.model.entity.CandidateEntity
import com.hos.service.model.entity.ContractEntity
import com.hos.service.model.record.CandidateBasicRecord
import com.hos.service.model.record.CandidateDetailsRecord
import com.hos.service.model.record.ContractBasicRecord
import org.springframework.stereotype.Component

@Component
class CandidateDetailsRecordFromCandidateEntityConverterImpl(
        private val contractConverter: Converter<ContractEntity, ContractBasicRecord>
) : RecordConverter<CandidateEntity, CandidateDetailsRecord>() {

    override fun create(source: CandidateEntity): CandidateDetailsRecord {
        return CandidateDetailsRecord(
                id = source.id,
                name = source.name,
                surname = source.surname,
                phone = source.phone,
                email = source.email,
                identificationNumber = source.identificationNumber,
                contracts = source.contracts.map(contractConverter::create)
        )
    }

}