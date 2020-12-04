package com.hos.service.model.entity

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
        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "HISTORY_LOG_ID")
        val history: HistoryLogEntity = HistoryLogEntity(),
        @OneToMany(mappedBy = "organisation", orphanRemoval = true, cascade = [CascadeType.ALL])
        val departments: MutableSet<DepartmentEntity> = mutableSetOf(),
        @OneToMany(mappedBy = "organisation", orphanRemoval = true, cascade = [CascadeType.ALL])
        val locations: MutableSet<LocationEntity> = mutableSetOf(),
        var name: String,
        var nip: String,
        var status: String
) : Serializable


@Entity(name = "DEPARTMENT")
class DepartmentEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DepartmentSeqGen")
        @SequenceGenerator(name = "DepartmentSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1,
        @Version
        val version: Int = 0,
        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "HISTORY_LOG_ID")
        val history: HistoryLogEntity = HistoryLogEntity(),
        @ManyToOne
        @JoinColumn(name = "ORGANISATION_ID")
        val organisation: OrganisationEntity,
        @OneToMany(mappedBy = "department")
        val staff: MutableSet<UserEntity> = mutableSetOf(),
        var name: String,
        var status: String
) : Serializable


@Entity(name = "LOCATION")
class LocationEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LocationSeqGen")
        @SequenceGenerator(name = "LocationSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1,
        @Version
        val version: Int = 0,
        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "HISTORY_LOG_ID")
        val history: HistoryLogEntity = HistoryLogEntity(),
        @ManyToOne
        @JoinColumn(name = "ORGANISATION_ID")
        val organisation: OrganisationEntity,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "ADDRESS_ID")
        val address: AddressEntity,
        var registeredOffice: Boolean,
        var name: String,
        var status: String
) : Serializable

