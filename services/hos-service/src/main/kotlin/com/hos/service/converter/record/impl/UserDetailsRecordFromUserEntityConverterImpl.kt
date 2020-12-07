package com.hos.service.converter.record.impl

import com.hos.service.converter.Converter
import com.hos.service.converter.record.RecordConverter
import com.hos.service.model.entity.AuthorityRoleEntity
import com.hos.service.model.entity.OrganisationEntity
import com.hos.service.model.entity.UserDeviceEntity
import com.hos.service.model.entity.UserEntity
import com.hos.service.model.record.OrganisationBasicRecord
import com.hos.service.model.record.UserDetailsRecord
import com.hos.service.model.record.UserDeviceRecord
import org.springframework.stereotype.Component

@Component
class UserDetailsRecordFromUserEntityConverterImpl(
        private val organisationConverter: Converter<OrganisationEntity, OrganisationBasicRecord>,
        private val deviceConverter: Converter<UserDeviceEntity, UserDeviceRecord>
) : RecordConverter<UserEntity, UserDetailsRecord>() {

    override fun create(source: UserEntity): UserDetailsRecord {
        return UserDetailsRecord(
                id = source.id,
                organisation = organisationConverter.create(source.organisation),
                version = source.version,
                login = source.login,
                password = "N/A",
                status = source.status,
                email = source.email,
                devices = source.devices.map(deviceConverter::create).toSet(),
                authorities = source.authorities.map(AuthorityRoleEntity::role).toSet()
        )
    }

}