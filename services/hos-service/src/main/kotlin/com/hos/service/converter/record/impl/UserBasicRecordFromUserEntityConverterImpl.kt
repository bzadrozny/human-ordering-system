package com.hos.service.converter.record.impl

import com.hos.service.converter.record.RecordConverter
import com.hos.service.model.entity.UserEntity
import com.hos.service.model.record.UserBasicRecord
import org.springframework.stereotype.Component

@Component
class UserBasicRecordFromUserEntityConverterImpl : RecordConverter<UserEntity, UserBasicRecord>() {

    override fun create(source: UserEntity): UserBasicRecord {
        return UserBasicRecord(
                id = source.id,
                login = source.login,
                email = source.email,
                status = source.status
        )
    }

}