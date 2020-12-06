package com.hos.service.model.converter.record

import com.hos.service.model.entity.UserEntity
import com.hos.service.model.record.UserBasicRecord
import org.springframework.stereotype.Component

@Component
class UserBasicRecordFromUserEntityConverterImpl : RecordConverter<UserEntity, UserBasicRecord>() {

    override fun create(source: UserEntity): UserBasicRecord {
        return UserBasicRecord(
                id = source.id,
                login = source.login,
                email = source.email
        )
    }

}