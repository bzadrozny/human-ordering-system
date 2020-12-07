package com.hos.service.model.entity

import com.hos.service.converter.jpa.impl.CommissionStatusEnumConverterImpl
import com.hos.service.converter.jpa.impl.EntityStatusEnumConverterImpl
import com.hos.service.model.enum.*
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*

@Entity(name = "COMMISSION")
class CommissionEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CommissionSeqGen")
        @SequenceGenerator(name = "CommissionSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1,
        @Version
        val version: Int = 0,
        val orderDate: LocalDate?,
        val decisionDate: LocalDate?,
        val completedDate: LocalDate?,
        var description: String?,
        @Convert(converter = CommissionStatusEnumConverterImpl::class)
        var status: CommissionStatus,
        @ManyToOne
        @JoinColumn(name = "PRINCIPAL_ID")
        var principal: UserEntity,
        @ManyToOne
        @JoinColumn(name = "EXECUTOR_ID")
        var executor: UserEntity,
        @ManyToOne
        @JoinColumn(name = "LOCATION_ID")
        var location: LocationEntity,
        @OneToMany(mappedBy = "commission")
        val records: MutableSet<CommissionRecordEntity> = mutableSetOf(),
        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "HISTORY_LOG_ID")
        val history: HistoryLogEntity = HistoryLogEntity()
) : Serializable

@Entity(name = "COMMISSION_RECORD")
class CommissionRecordEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CommissionRecSeqGen")
        @SequenceGenerator(name = "CommissionRecSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1,
        @Version
        val version: Int = 0,
        var jobName: String,
        var ordered: Int,
        var acceptedOrdered: Int,
        var recruited: Int,
        var startDate: LocalDate,
        var acceptedStartDate: LocalDate,
        var endDate: LocalDate,
        var wageRateMin: Float,
        var wageRateMax: Float,
        var settlementType: SettlementType,
        var description: String,
        @Convert(converter = CommissionStatusEnumConverterImpl::class)
        var status: CommissionRecordStatus,
        @OneToMany(mappedBy = "commissionRecord")
        val contracts: MutableSet<ContractEntity> = mutableSetOf(),
        @ManyToOne
        @JoinColumn(name = "COMMISSION_ID")
        val commission: CommissionEntity,
        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "HISTORY_LOG_ID")
        val history: HistoryLogEntity = HistoryLogEntity()
) : Serializable