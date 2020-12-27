package com.hos.service.converter.entity.impl

import com.hos.service.converter.Converter
import com.hos.service.converter.MultiConverter
import com.hos.service.model.entity.CommissionEntity
import com.hos.service.model.entity.CommissionRecordEntity
import com.hos.service.model.form.CommissionForm
import com.hos.service.model.form.CommissionRecordForm
import com.hos.service.repository.LocationRepository
import com.hos.service.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class CommissionEntityFromCommissionFormConverterImpl(
    private val userRepository: UserRepository,
    private val locationRepository: LocationRepository,
    private val recordConverter: MultiConverter<CommissionRecordForm, CommissionEntity, CommissionRecordEntity>
) : Converter<CommissionForm, CommissionEntity> {

    override fun create(source: CommissionForm): CommissionEntity {
        val target = CommissionEntity(
            status = source.status!!,
            principal = userRepository.getOne(source.principal!!),
            location = locationRepository.getOne(source.location!!),
            description = source.description
        )
        source.records?.forEach {
            target.records.add(recordConverter.create(it, target))
        }
        return target
    }

    override fun merge(source: CommissionForm, target: CommissionEntity): CommissionEntity {
        source.status?.let { target.status = it }
        source.principal?.let {
            if (target.principal.id != it) {
                target.principal = userRepository.getOne(it)
            }
        }
        source.location?.let {
            if (target.location.id != it) {
                target.location = locationRepository.getOne(it)
            }
        }
        target.description = source.description

        target.records.removeIf {
            source.records!!.none { record -> record.id == it.id }
        }
        target.records.forEach {
            recordConverter.merge(source.records!!.first { source -> source.id == it.id }, target, it)
        }
        source.records?.filter { it.id == null }?.forEach {
            target.records.add(recordConverter.create(it, target))
        }

        return target
    }

}