package com.hos.service.model.entity

import com.hos.service.model.record.UserBasicsRecord
import com.hos.service.model.record.UserDetailsRecord
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "USER")
class UserEntity(
        @Column(unique = true)
        var login: String,
        @Column(unique = true)
        var email: String,
        var passwrod: String
) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserSeqGen")
    @SequenceGenerator(name = "UserSeqGen", initialValue = 1000, allocationSize = 10)
    val id: Long = -1

    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, orphanRemoval = true, cascade = [CascadeType.ALL])
    val devices: MutableList<UserDeviceEntity> = mutableListOf()

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true, cascade = [CascadeType.ALL])
    val authorities: MutableList<AuthoritisRoleEntity> = mutableListOf()


    fun print(): String {
        return "id: $id, login: $login, email: $email, pass: $passwrod, roles: ${authorities.printRoles()}, agents: ${devices.printAgents()}"
    }

    private fun MutableList<UserDeviceEntity>.printAgents(): String {
        return if (isEmpty()) "null" else map { it.agentType }.reduce { acc, ob -> "$ob, $acc" }
    }

    private fun MutableList<AuthoritisRoleEntity>.printRoles(): String {
        return if (isEmpty()) "null" else map { it.role.desc }.reduce { acc, ob -> "$ob, $acc" }
    }

    fun mapToUserDetailsRecord(): UserDetailsRecord {
        return UserDetailsRecord(id, login, email, "N/A", devices, authorities.map { a -> a.role })
    }

    fun mapToUserBasicsRecord(): UserBasicsRecord {
        return UserBasicsRecord(id, login, email)
    }
}
