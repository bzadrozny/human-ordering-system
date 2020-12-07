package com.hos.service.service.impl

import com.hos.service.converter.Converter
import com.hos.service.converter.MultiConverter
import com.hos.service.model.entity.DepartmentEntity
import com.hos.service.model.entity.OrganisationEntity
import com.hos.service.model.enum.QualifierType
import com.hos.service.model.enum.Resource
import com.hos.service.model.exception.ResourceNotFoundException
import com.hos.service.model.exception.ValidationException
import com.hos.service.model.form.DepartmentForm
import com.hos.service.model.record.OrganisationDetailsRecord
import com.hos.service.repo.OrganisationRepository
import com.hos.service.service.DepartmentService
import com.hos.service.validator.FormValidator
import org.springframework.stereotype.Service

@Service
class DepartmentServiceImpl(
        private val departmentValidator: FormValidator<DepartmentForm, OrganisationEntity>,
        private val departmentConverter: MultiConverter<DepartmentForm, OrganisationEntity, DepartmentEntity>,
        private val organisationDetailsConverter: Converter<OrganisationEntity, OrganisationDetailsRecord>,
        private val organisationRepository: OrganisationRepository
) : DepartmentService {

    override fun addDepartment(form: DepartmentForm): OrganisationDetailsRecord {
        departmentValidator.validateInitiallyBeforeRegistration(form).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }

        val organisation = organisationRepository.findById(form.organisation!!).orElseThrow {
            ResourceNotFoundException(
                    Resource.ORGANISATION,
                    QualifierType.ID,
                    "${form.organisation}"
            )
        }
        departmentValidator.validateBeforeModification(form, organisation).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }

        departmentConverter.create(form, organisation).let {
            organisation.departments.add(it)
        }

        return organisationRepository.save(organisation)
                .let { organisationDetailsConverter.create(it) }
    }

    override fun modifyDepartment(form: DepartmentForm): OrganisationDetailsRecord {
        departmentValidator.validateInitiallyBeforeModification(form).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }

        val organisation = organisationRepository.findById(form.organisation!!).orElseThrow {
            ResourceNotFoundException(
                    Resource.ORGANISATION,
                    QualifierType.ID,
                    "${form.organisation}"
            )
        }
        departmentValidator.validateBeforeModification(form, organisation).let {
            if (it.hasBlocker()) throw ValidationException(it)
        }

        organisation.departments
                .first { form.id == it.id }
                .let { departmentConverter.merge(form, organisation, it) }

        return organisationRepository.save(organisation)
                .let { organisationDetailsConverter.create(it) }
    }

}