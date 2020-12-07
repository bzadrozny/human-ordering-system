package com.hos.service.service.impl

import com.hos.service.converter.Converter
import com.hos.service.converter.MultiConverter
import com.hos.service.model.entity.LocationEntity
import com.hos.service.model.entity.OrganisationEntity
import com.hos.service.model.enum.QualifierType
import com.hos.service.model.enum.Resource
import com.hos.service.model.exception.ResourceNotFoundException
import com.hos.service.model.exception.ValidationException
import com.hos.service.model.form.LocationForm
import com.hos.service.model.record.OrganisationDetailsRecord
import com.hos.service.repo.OrganisationRepository
import com.hos.service.service.LocationService
import com.hos.service.validator.FormValidator
import org.springframework.stereotype.Service

@Service
class LocationServiceImpl(
        private val locationValidator: FormValidator<LocationForm, OrganisationEntity>,
        private val locationConverter: MultiConverter<LocationForm, OrganisationEntity, LocationEntity>,
        private val organisationDetailsConverter: Converter<OrganisationEntity, OrganisationDetailsRecord>,
        private val organisationRepository: OrganisationRepository
) : LocationService {

    override fun addLocation(form: LocationForm): OrganisationDetailsRecord {
        locationValidator.validateInitiallyBeforeRegistration(form).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }

        val organisation = organisationRepository.findById(form.organisation!!).orElseThrow {
            ResourceNotFoundException(
                    Resource.ORGANISATION,
                    QualifierType.ID,
                    "${form.organisation}"
            )
        }
        locationValidator.validateBeforeModification(form, organisation).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }

        locationConverter.create(form, organisation).let {
            organisation.locations.add(it)
        }

        return organisationRepository.save(organisation)
                .let { organisationDetailsConverter.create(it) }
    }

    override fun modifyLocation(form: LocationForm): OrganisationDetailsRecord {
        locationValidator.validateInitiallyBeforeRegistration(form).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }

        val organisation = organisationRepository.findById(form.organisation!!).orElseThrow {
            ResourceNotFoundException(
                    Resource.ORGANISATION,
                    QualifierType.ID,
                    "${form.organisation}"
            )
        }
        locationValidator.validateBeforeModification(form, organisation).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }

        organisation.locations
                .first { form.id == it.id }
                .let { locationConverter.merge(form, organisation, it) }

        return organisationRepository.save(organisation)
                .let { organisationDetailsConverter.create(it) }
    }

}