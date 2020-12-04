package com.hos.service.model.converter.record

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