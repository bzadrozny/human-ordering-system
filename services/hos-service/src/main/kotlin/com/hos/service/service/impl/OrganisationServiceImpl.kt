package com.hos.service.service.impl

import com.hos.service.converter.Converter
import com.hos.service.model.entity.OrganisationEntity
import com.hos.service.model.enum.QualifierType
import com.hos.service.model.enum.Resource
import com.hos.service.model.exception.ResourceNotFoundException
import com.hos.service.model.exception.ValidationException
import com.hos.service.model.form.OrganisationForm
import com.hos.service.model.record.OrganisationBasicRecord
import com.hos.service.model.record.OrganisationDetailsRecord
import com.hos.service.repo.OrganisationRepository
import com.hos.service.service.OrganisationService
import com.hos.service.validator.FormValidator
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class OrganisationServiceImpl(
        private val organisationFormValidator: FormValidator<OrganisationForm, OrganisationEntity>,
        private val organisationConverter: Converter<OrganisationForm, OrganisationEntity>,
        private val organisationBasicConverter: Converter<OrganisationEntity, OrganisationBasicRecord>,
        private val organisationDetailsConverter: Converter<OrganisationEntity, OrganisationDetailsRecord>,
        private val organisationRepository: OrganisationRepository
) : OrganisationService {

    override fun findAllOrganisations(): List<OrganisationBasicRecord> {
        return organisationRepository.findAll().map(organisationBasicConverter::create)
    }

    override fun findOrganisationById(id: Long): OrganisationDetailsRecord? {
        return organisationRepository.findById(id)
                .map(organisationDetailsConverter::create)
                .orElseThrow { ResourceNotFoundException(Resource.ORGANISATION, QualifierType.ID, "$id") }
    }

    @Transactional
    override fun registerOrganisation(organisationForm: OrganisationForm): OrganisationDetailsRecord? {
        organisationFormValidator.validateInitiallyBeforeRegistration(organisationForm).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }
        organisationFormValidator.validateBeforeRegistration(organisationForm).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }
        return organisationConverter.create(organisationForm)
                .let { organisationRepository.save(it) }
                .let { organisationDetailsConverter.create(it) }
    }

    @Transactional
    override fun modifyOrganisation(organisationForm: OrganisationForm): OrganisationDetailsRecord? {
        organisationFormValidator.validateInitiallyBeforeModification(organisationForm).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }
        val organisationEntity = organisationRepository.findById(organisationForm.id!!).orElseThrow {
            ResourceNotFoundException(
                    Resource.ORGANISATION,
                    QualifierType.ID,
                    "${organisationForm.id}"
            )
        }
        organisationFormValidator.validateBeforeModification(organisationForm, organisationEntity).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }
        return organisationConverter.merge(organisationForm, organisationEntity)
                .let { organisationRepository.save(it) }
                .let { organisationDetailsConverter.create(it) }
    }

}