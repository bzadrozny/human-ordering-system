package com.hos.service.model.converter.record

import com.hos.service.model.converter.Converter
import com.hos.service.model.entity.AuthorityRoleEntity
import com.hos.service.model.entity.UserDeviceEntity
import com.hos.service.model.entity.UserEntity
import com.hos.service.model.record.UserDetailsRecord
import com.hos.service.model.record.UserDeviceRecord
import org.springframework.stereotype.Component

@Component
class UserDetailsRecordFromUserEntityConverterImpl(
        private val deviceConverter: Converter<UserDeviceEntity, UserDeviceRecord>
) : RecordConverter<UserEntity, UserDetailsRecord>() {

    override fun create(source: UserEntity): UserDetailsRecord {
        return UserDetailsRecord(
                id = source.id,
                organisation = source.organisation.id,
                version = source.version,
                login = source.login,
                password = "N/A",
                email = source.email,
                devices = source.devices.map(deviceConverter::create).toSet(),
                authorities = source.authorities.map(AuthorityRoleEntity::role).toSet()
        )
    }

}