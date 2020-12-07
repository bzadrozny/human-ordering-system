package com.hos.service.converter.record.impl

import com.hos.service.converter.record.RecordConverter
import com.hos.service.model.entity.UserDeviceEntity
import com.hos.service.model.record.UserDeviceRecord
import org.springframework.stereotype.Component

@Component
class UserDeviceRecordFromUserDeviceEntityConverterImpl : RecordConverter<UserDeviceEntity, UserDeviceRecord>() {

    override fun create(source: UserDeviceEntity): UserDeviceRecord {
        return UserDeviceRecord(
                id = source.id,
                version = source.version,
                agentType = source.agentType,
                ipAddress = source.ipAddress
        )
    }

}