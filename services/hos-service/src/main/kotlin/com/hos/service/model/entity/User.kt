package com.hos.service.model.entity

import com.hos.service.model.converter.jpa.AuthorityEnumConverterImpl
import com.hos.service.model.enum.Authority
import com.hos.service.model.record.UserBasicsRecord
import com.hos.service.model.record.UserDetailsRecord
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "USER")
class UserEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserSeqGen")
        @SequenceGenerator(name = "UserSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1,
        @Column(unique = true)
        var login: String,
        @Column(unique = true)
        var email: String,
        var passwrod: String,
        @ManyToOne
        @JoinColumn(name = "DEPARTMENT_ID")
        val department: DepartmentEntity,
        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true, cascade = [CascadeType.ALL])
        val devices: MutableList<UserDeviceEntity> = mutableListOf(),
        @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true, cascade = [CascadeType.ALL])
        val authorities: MutableList<AuthoritisRoleEntity> = mutableListOf()
) : Serializable {
    fun mapToUserDetailsRecord(): UserDetailsRecord {
        return UserDetailsRecord(id, login, email, "N/A", devices, authorities.map { a -> a.role })
    }

    fun mapToUserBasicsRecord(): UserBasicsRecord {
        return UserBasicsRecord(id, login, email)
    }

    fun print(): String {
        return "id: $id, login: $login, email: $email, pass: $passwrod, roles: ${authorities.printRoles()}, agents: ${devices.printAgents()}"
    }

    private fun MutableList<UserDeviceEntity>.printAgents(): String {
        return if (isEmpty()) "null" else map { it.agentType }.reduce { acc, ob -> "$ob, $acc" }
    }

    private fun MutableList<AuthoritisRoleEntity>.printRoles(): String {
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
class AuthoritisRoleEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AuthRoleSeqGen")
        @SequenceGenerator(name = "AuthRoleSeqGen")
        val id: Long = -1,
        @Column(name = "USER_ID")
        val user: Long,
        @Column(name = "ROLE")
        @Convert(converter = AuthorityEnumConverterImpl::class)
        var role: Authority
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
        @Column(name = "USER_ID")
        val user: Long,
        @Column(name = "AGENT_TYPE")
        val agentType: String,
        @Column(name = "IP_ADDRESS")
        val ipAddress: String
) : Serializable