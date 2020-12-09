package com.hos.service.converter.record.impl

import com.hos.service.converter.Converter
import com.hos.service.converter.record.RecordConverter
import com.hos.service.model.entity.CandidateEntity
import com.hos.service.model.entity.ContractEntity
import com.hos.service.model.entity.UserEntity
import com.hos.service.model.record.CandidateBasicRecord
import com.hos.service.model.record.ContractDetailsRecord
import com.hos.service.model.record.UserBasicRecord
import org.springframework.stereotype.Component

@Component
class ContractDetailsRecordFromContractEntityConverterImpl(
        private val userConverter: Converter<UserEntity, UserBasicRecord>,
        private val candidateConverter: Converter<CandidateEntity, CandidateBasicRecord>
) : RecordConverter<ContractEntity, ContractDetailsRecord>() {

    override fun create(source: ContractEntity): ContractDetailsRecord {
        return ContractDetailsRecord(
                id = source.id,
                code = source.code,
                contractDate = source.contractDate,
                startDate = source.startDate,
                endDate = source.endDate,
                contractType = source.contractType,
                salary = source.salary,
                recruiter = source.recruiter.let(userConverter::create),
                candidate = source.candidate?.let(candidateConverter::create),
                description = source.description,
                approved = source.approved
        )
    }

}