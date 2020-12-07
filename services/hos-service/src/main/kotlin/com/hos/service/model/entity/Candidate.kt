package com.hos.service.model.entity

import java.io.Serializable
import javax.persistence.*

@Entity(name = "CANDIDATE")
class CandidateEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CandidateSeqGen")
        @SequenceGenerator(name = "CandidateSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1,
        @Version
        val version: Int = 0,
        var name: String,
        var surname: String,
        var phone: String,
        var email: String,
        var identificationNumber: String,
        @OneToOne
        @JoinColumn(name = "ADDRESS_ID")
        val address: AddressEntity,
        @OneToMany(mappedBy = "candidate")
        val contracts: MutableSet<ContractEntity>,
        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "HISTORY_LOG_ID")
        val history: HistoryLogEntity = HistoryLogEntity()
) : Serializable