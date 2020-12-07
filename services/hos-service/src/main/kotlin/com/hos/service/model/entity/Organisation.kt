package com.hos.service.model.entity

import com.hos.service.converter.jpa.impl.EntityStatusEnumConverterImpl
import com.hos.service.model.enum.EntityStatus
import java.io.Serializable
import javax.persistence.*

@Entity(name = "ORGANISATION")
class OrganisationEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrganisationSeqGen")
        @SequenceGenerator(name = "OrganisationSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1,
        @Version
        val version: Int = 0,
        var name: String,
        var nip: String,
        @Convert(converter = EntityStatusEnumConverterImpl::class)
        var status: EntityStatus,
        @OneToMany(mappedBy = "organisation", orphanRemoval = true, cascade = [CascadeType.ALL])
        val departments: MutableSet<DepartmentEntity> = mutableSetOf(),
        @OneToMany(mappedBy = "organisation", orphanRemoval = true, cascade = [CascadeType.ALL])
        val locations: MutableSet<LocationEntity> = mutableSetOf(),
        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "HISTORY_LOG_ID")
        val history: HistoryLogEntity = HistoryLogEntity()
) : Serializable


@Entity(name = "DEPARTMENT")
class DepartmentEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DepartmentSeqGen")
        @SequenceGenerator(name = "DepartmentSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1,
        @Version
        val version: Int = 0,
        var name: String,
        @Convert(converter = EntityStatusEnumConverterImpl::class)
        var status: EntityStatus,
        @ManyToOne
        @JoinColumn(name = "ORGANISATION_ID")
        val organisation: OrganisationEntity,
        @OneToMany(mappedBy = "department")
        val staff: MutableSet<UserEntity> = mutableSetOf(),
        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "HISTORY_LOG_ID")
        val history: HistoryLogEntity = HistoryLogEntity()
) : Serializable


@Entity(name = "LOCATION")
class LocationEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LocationSeqGen")
        @SequenceGenerator(name = "LocationSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1,
        @Version
        val version: Int = 0,
        var name: String,
        @Convert(converter = EntityStatusEnumConverterImpl::class)
        var status: EntityStatus,
        var registeredOffice: Boolean,
        @ManyToOne
        @JoinColumn(name = "ORGANISATION_ID")
        val organisation: OrganisationEntity,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "ADDRESS_ID")
        val address: AddressEntity,
        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "HISTORY_LOG_ID")
        val history: HistoryLogEntity = HistoryLogEntity()
) : Serializable

