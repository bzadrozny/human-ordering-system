package com.hos.service.service.impl

import com.hos.service.converter.Converter
import com.hos.service.converter.MultiConverter
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.entity.CommissionRecordEntity
import com.hos.service.model.entity.ContractEntity
import com.hos.service.model.enum.QualifierType
import com.hos.service.model.enum.Resource
import com.hos.service.model.exception.ResourceNotFoundException
import com.hos.service.model.exception.ValidationException
import com.hos.service.model.form.ContractForm
import com.hos.service.model.record.ContractDetailsRecord
import com.hos.service.repository.CommissionRepository
import com.hos.service.repository.ContractRepository
import com.hos.service.service.ContractService
import com.hos.service.validator.FormValidator
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ContractServiceImpl(
        private val contractValidator: FormValidator<ContractForm, CommissionEntity>,
        private val contractDetailsConverter: Converter<ContractEntity, ContractDetailsRecord>,
        private val contractConverter: MultiConverter<ContractForm, CommissionRecordEntity, ContractEntity>,
        private val contractRepository: ContractRepository,
        private val commissionRepository: CommissionRepository
) : ContractService {

    override fun findContractDetailsById(contractId: Long): ContractDetailsRecord {
        return contractRepository.findByIdOrNull(contractId)
                ?.let { contractDetailsConverter.create(it) }
                ?: throw ResourceNotFoundException(Resource.CONTRACT, QualifierType.ID, "$contractId")
    }

    override fun registerContract(form: ContractForm): ContractDetailsRecord {
        contractValidator.validateInitiallyBeforeRegistration(form).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }

        val commission = commissionRepository.findByIdOrNull(form.commission)
                ?: throw ResourceNotFoundException(Resource.COMMISSION, QualifierType.ID, "${form.commission}")

        contractValidator.validateComplexBeforeModification(form, commission).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }

        val record = commission.records.firstOrNull { it.id == form.commissionRecord }
                ?: throw ResourceNotFoundException(Resource.COMMISSION_RECORD, QualifierType.ID, "${form.commissionRecord}")

        return contractConverter.create(form, record)
                .let { contractRepository.save(it) }
                .let { contractDetailsConverter.create(it) }
    }

    override fun modifyContract(form: ContractForm): ContractDetailsRecord {
        contractValidator.validateInitiallyBeforeModification(form).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }

        val commission = commissionRepository.findByIdOrNull(form.commission)
                ?: throw ResourceNotFoundException(Resource.COMMISSION, QualifierType.ID, "${form.commission}")

        contractValidator.validateComplexBeforeModification(form, commission).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }

        val record = commission.records.firstOrNull { it.id == form.commissionRecord }
                ?: throw ResourceNotFoundException(Resource.COMMISSION_RECORD, QualifierType.ID, "${form.commissionRecord}")

        val contract = record.contracts.firstOrNull { it.id == form.id }
                ?: throw ResourceNotFoundException(Resource.CONTRACT, QualifierType.ID, "${form.id}")

        return contractConverter.merge(form, record, contract)
                .let { contractRepository.save(it) }
                .let { contractDetailsConverter.create(it) }
    }

}