package com.hos.service.service.impl

import com.hos.service.converter.Converter
import com.hos.service.converter.MultiConverter
import com.hos.service.model.common.Validation
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.entity.CommissionRecordEntity
import com.hos.service.model.enum.*
import com.hos.service.model.exception.ResourceNotFoundException
import com.hos.service.model.exception.ValidationException
import com.hos.service.model.form.CommissionRecordForm
import com.hos.service.model.record.CommissionRecordDetailsRecord
import com.hos.service.repository.CommissionRecordRepository
import com.hos.service.repository.CommissionRepository
import com.hos.service.service.CommissionRecordService
import com.hos.service.utils.validateRequiredField
import com.hos.service.validator.FormValidator
import org.springframework.stereotype.Service

@Service
class CommissionRecordServiceImpl(
        private val commissionRecordValidator: FormValidator<CommissionRecordForm, CommissionEntity>,
        private val recordConverter: MultiConverter<CommissionRecordForm, CommissionEntity, CommissionRecordEntity>,
        private val recordDetailsConverter: Converter<CommissionRecordEntity, CommissionRecordDetailsRecord>,
        private val recordRepository: CommissionRecordRepository,
        private val commissionRepository: CommissionRepository
) : CommissionRecordService {

    override fun addRecord(form: CommissionRecordForm): CommissionRecordDetailsRecord {
        commissionRecordValidator.validateInitiallyBeforeRegistration(form).let {
            it.addValidation(validateRequiredField(form.commission, "id zamówienia", "commission"))
            if (it.hasBlocker()) throw ValidationException(it)
        }

        val commission = commissionRepository.findById(form.commission!!).orElseThrow {
            ResourceNotFoundException(
                    Resource.COMMISSION,
                    QualifierType.ID,
                    "${form.id}"
            )
        }
        commissionRecordValidator.validateComplexBeforeModification(form, commission).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }

        return recordConverter.create(form, commission)
                .let { recordRepository.save(it) }
                .let { recordDetailsConverter.create(it) }
    }

    override fun modifyRecord(form: CommissionRecordForm): CommissionRecordDetailsRecord {
        commissionRecordValidator.validateInitiallyBeforeModification(form).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }

        val commission = commissionRepository.findById(form.commission!!).orElseThrow {
            ResourceNotFoundException(
                    Resource.COMMISSION,
                    QualifierType.ID,
                    "${form.commission}"
            )
        }

        commissionRecordValidator.validateComplexBeforeModification(form, commission).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }

        val record = commission.records.firstOrNull { it.id == form.id }
                ?: throw ResourceNotFoundException(
                        Resource.COMMISSION_RECORD,
                        QualifierType.ID,
                        "${form.id}"
                )

        return recordConverter.merge(form, commission, record)
                .let { recordRepository.save(it) }
                .let { recordDetailsConverter.create(it) }
    }

    override fun cancelRecord(commissionId: Long, commissionRecordId: Long): Boolean {
        val commission = commissionRepository.findById(commissionId).orElseThrow {
            ResourceNotFoundException(
                    Resource.COMMISSION,
                    QualifierType.ID,
                    "$commissionId"
            )
        }

        val validation = Validation("commission")
        if (commission.status in listOf(CommissionStatus.SENT, CommissionStatus.REJECTED, CommissionStatus.COMPLETED)) {
            validation.addValidation(
                    "Brak możliwości anulowania rekordu zablokowanego lub zakończonego zamówienia",
                    "status",
                    ValidationStatus.BLOCKER
            )
        }

        val record = commission.records.firstOrNull { it.id == commissionRecordId }
        if (record == null) validation.addValidation(
                "W zamówieniu o id '$commissionId' nie ma rekordu o id: '$commissionRecordId'",
                "records.id",
                ValidationStatus.BLOCKER
        ) else if (record.status !in listOf(CommissionRecordStatus.CREATED, CommissionRecordStatus.MODIFIED, CommissionRecordStatus.ACCEPTED)) validation.addValidation(
                "Nie ma możliwości anulowania zablokowanego lub zakończonego rekordu",
                "records.id",
                ValidationStatus.BLOCKER
        )

        if (validation.hasBlocker()) throw ValidationException(validation)

        return record?.let {
            it.status = CommissionRecordStatus.CANCELED
            recordRepository.save(it)
            true
        } ?: false
    }

}