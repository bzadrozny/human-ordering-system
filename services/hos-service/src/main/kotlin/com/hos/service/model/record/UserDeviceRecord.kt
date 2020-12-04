package com.hos.service.model.record

data class UserDeviceRecord(
        val id: Long,
        val version: Int,
        val agentType: String,
        val ipAddress: String
)