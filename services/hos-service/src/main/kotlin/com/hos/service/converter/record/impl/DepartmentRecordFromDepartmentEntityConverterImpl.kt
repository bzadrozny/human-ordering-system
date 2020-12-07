package com.hos.service.converter.record.impl

import com.hos.service.converter.Converter
import com.hos.service.converter.record.RecordConverter
import com.hos.service.model.entity.DepartmentEntity
import com.hos.service.model.entity.UserEntity
import com.hos.service.model.record.DepartmentRecord
import com.hos.service.model.record.UserBasicRecord
import org.springframework.stereotype.Component

@Component
class DepartmentRecordFromDepartmentEntityConverterImpl(
        private val userConverter: Converter<UserEntity, UserBasicRecord>
) : RecordConverter<DepartmentEntity, DepartmentRecord>() {

    override fun create(source: DepartmentEntity): DepartmentRecord {
        return DepartmentRecord(
                id = source.id,
                name = source.name,
                staff = source.staff.map(userConverter::create),
                status = source.status
        )
    }

}