package com.hos.service.model.entity

import com.hos.service.model.converter.jpa.AuthorityEnumConverterImpl
import com.hos.service.model.enum.Authority
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(
        name = "AUTH_ROLE",
        uniqueConstraints = [
            UniqueConstraint(columnNames = ["USER_ID", "ROLE"])
        ]
)
class AuthoritisRoleEntity(
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
        val user: UserEntity,
        @Convert(converter = AuthorityEnumConverterImpl::class)
        val role: Authority
) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AuthRoleSeqGen")
    @SequenceGenerator(name = "AuthRoleSeqGen")
    val id: Long = -1
}