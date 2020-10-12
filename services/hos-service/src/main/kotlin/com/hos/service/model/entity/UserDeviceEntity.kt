package com.hos.service.model.entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(
        name = "USR_DEV",
        uniqueConstraints = [
            UniqueConstraint(columnNames = ["USER_ID", "AGENT_TYPE", "IP_ADDRESS"])
        ]
)
class UserDeviceEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserDevSeqGen")
        @SequenceGenerator(name = "UserDevSeqGen")
        val id: Long,
        @Column(name = "USER_ID")
        val userId: Long,
        @Column(name = "AGENT_TYPE")
        val agentType: String,
        @Column(name = "IP_ADDRESS")
        val ipAddress: String
) : Serializable