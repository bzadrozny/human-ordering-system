package com.hos.service.model.entity

import com.hos.service.converter.jpa.impl.AuthorityEnumConverterImpl
import com.hos.service.converter.jpa.impl.EntityStatusEnumConverterImpl
import com.hos.service.model.enum.Authority
import com.hos.service.model.enum.EntityStatus

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "USER")
class UserEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserSeqGen")
        @SequenceGenerator(name = "UserSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1,
        @Version
        val version: Int = 0,
        @Convert(converter = EntityStatusEnumConverterImpl::class)
        var status: EntityStatus,
        @Column(unique = true)
        var login: String,
        @Column(unique = true)
        var email: String,
        var password: String,
        var name: String,
        var surname: String,
        var phone1: String,
        var phone2: String? = null,
        @ManyToOne
        @JoinColumn(name = "SUPERIOR_ID")
        var superior: UserEntity? = null,
        @ManyToOne
        @JoinTable(name = "ORGANISATION_ID")
        var organisation: OrganisationEntity,
        @ManyToOne
        @JoinColumn(name = "DEPARTMENT_ID")
        var department: DepartmentEntity,
        @ManyToOne
        @JoinColumn(name = "LOCATION_ID")
        var location: LocationEntity,
        @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = [CascadeType.ALL])
        val authorities: MutableList<AuthorityRoleEntity> = mutableListOf(),
        @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = [CascadeType.ALL])
        val devices: MutableList<UserDeviceEntity> = mutableListOf(),
        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "HISTORY_LOG_ID")
        val history: HistoryLogEntity = HistoryLogEntity()
) : Serializable {
    fun print(): String {
        return "id: $id, login: $login, email: $email, pass: $password, roles: ${authorities.printRoles()}, agents: ${devices.printAgents()}"
    }

    private fun MutableList<UserDeviceEntity>.printAgents(): String {
        return if (isEmpty()) "null" else map { it.agentType }.reduce { acc, ob -> "$ob, $acc" }
    }

    private fun MutableList<AuthorityRoleEntity>.printRoles(): String {
        return if (isEmpty()) "null" else map { it.role.desc }.reduce { acc, ob -> "$ob, $acc" }
    }
}


@Entity
@Table(
        name = "AUTH_ROLE",
        uniqueConstraints = [
            UniqueConstraint(columnNames = ["USER_ID", "ROLE"])
        ]
)
class AuthorityRoleEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AuthRoleSeqGen")
        @SequenceGenerator(name = "AuthRoleSeqGen")
        val id: Long = -1,
        @Version
        val version: Int = 0,
        @ManyToOne
        @JoinColumn(name = "USER_ID")
        val user: UserEntity,
        @Convert(converter = AuthorityEnumConverterImpl::class)
        val role: Authority,
        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "HISTORY_LOG_ID")
        val history: HistoryLogEntity = HistoryLogEntity()
) : Serializable


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
        @Version
        val version: Int = 0,
        @Column(name = "USER_ID")
        val user: Long,
        @Column(name = "AGENT_TYPE")
        val agentType: String,
        @Column(name = "IP_ADDRESS")
        val ipAddress: String,
        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "HISTORY_LOG_ID")
        val history: HistoryLogEntity = HistoryLogEntity()
) : Serializable