package com.hos.service.model.converter.record

import com.hos.service.model.entity.UserEntity
import com.hos.service.model.record.UserBasicsRecord
import org.springframework.stereotype.Component

@Component
class UserBasicsRecordFromUserEntityConverterImpl : RecordConverter<UserEntity, UserBasicsRecord>() {

    override fun create(source: UserEntity): UserBasicsRecord {
        return UserBasicsRecord(
                id = source.id,
                login = source.login,
                email = source.email
        )
    }

}