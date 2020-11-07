package com.hos.service.model.entity

import java.io.Serializable
import javax.persistence.*

@Entity(name = "ORGANISATION")
class OrganisationEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrganisationSeqGen")
        @SequenceGenerator(name = "OrganisationSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1,
        var name: String,
        var nip: String,
        @OneToMany(mappedBy = "organisation", orphanRemoval = true, cascade = [CascadeType.ALL])
        val departments: MutableSet<DepartmentEntity> = mutableSetOf(),
        @OneToMany(mappedBy = "organisation", orphanRemoval = true, cascade = [CascadeType.ALL])
        val locations: MutableSet<LocationEntity> = mutableSetOf(),
        var status: String
) : Serializable


@Entity(name = "DEPARTMENT")
class DepartmentEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DepartmentSeqGen")
        @SequenceGenerator(name = "DepartmentSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1,
        var name: String,
        @ManyToOne
        @JoinColumn(name = "ORGANISATION_ID")
        val organisation: OrganisationEntity,
        @OneToMany(mappedBy = "department")
        val staff: MutableSet<UserEntity> = mutableSetOf(),
        var status: String
) : Serializable


@Entity(name = "LOCATION")
class LocationEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LocationSeqGen")
        @SequenceGenerator(name = "LocationSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1,
        val name: String,
        @ManyToOne
        @JoinColumn(name = "organisation")
        val organisation: OrganisationEntity,
        @OneToOne
        @MapsId
        val address: AddressEntity,
        @Column(name = "REGISTERED_OFFICE")
        val registeredOffice: Boolean,
        val status: String
) : Serializable


@Entity(name = "ADDRESS")
class AddressEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AddressSeqGen")
        @SequenceGenerator(name = "AddressSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1,
        var street: String,
        var number: String,
        var city: String,
        @Column(name = "POSTAL_CODE")
        var postalCode: String
) : Serializable