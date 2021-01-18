package com.hos.service.converter.entity.impl

import com.hos.service.converter.MultiConverter
import com.hos.service.model.entity.CommissionRecordEntity
import com.hos.service.model.entity.ContractEntity
import com.hos.service.model.form.ContractForm
import com.hos.service.repository.CandidateRepository
import com.hos.service.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class ContractEntityFromContractFormConverterImpl(
        private val userRepository: UserRepository,
        private val candidateRepository: CandidateRepository
) : MultiConverter<ContractForm, CommissionRecordEntity, ContractEntity> {

    override fun create(source1: ContractForm, source2: CommissionRecordEntity): ContractEntity {
        return ContractEntity(
                code = source1.code!!,
                contractDate = source1.contractDate!!,
                startDate = source1.startDate!!,
                endDate = source1.endDate,
                salary = source1.salary!!,
                contractType = source1.contractType!!,
                description = source1.description,
                commissionRecord = source2,
                recruiter = userRepository.getOne(source1.recruiter!!),
                candidate = source1.candidate?.let { candidateRepository.getOne(it) },
        )
    }

    override fun merge(source1: ContractForm, source2: CommissionRecordEntity, target: ContractEntity): ContractEntity {

        source1.code?.let { target.code = it }
        source1.contractDate?.let { target.contractDate = it }
        source1.startDate?.let { target.startDate = it }
        source1.endDate?.let { target.endDate = it }
        source1.salary?.let { target.salary = it }
        source1.contractType?.let { target.contractType = it }
        target.description = source1.description
        source1.candidate?.let { target.candidate = candidateRepository.getOne(it) }

        return target
    }

}