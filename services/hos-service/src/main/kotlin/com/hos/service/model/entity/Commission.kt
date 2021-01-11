package com.hos.service.model.entity

import com.hos.service.converter.jpa.impl.CommissionRecordStatusEnumConverterImpl
import com.hos.service.converter.jpa.impl.CommissionStatusEnumConverterImpl
import com.hos.service.converter.jpa.impl.EntityStatusEnumConverterImpl
import com.hos.service.converter.jpa.impl.SettlementTypeEnumConverterImpl
import com.hos.service.model.enum.*
import lombok.Data
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
    var orderDate: LocalDate? = null,
    var decisionDate: LocalDate? = null,
    var realisationDate: LocalDate? = null,
    var completedDate: LocalDate? = null,
    var description: String?,
    var decisionDescription: String? = null,
    @Convert(converter = CommissionStatusEnumConverterImpl::class)
    var status: CommissionStatus,
    @ManyToOne
    @JoinColumn(name = "PRINCIPAL_ID")
    var principal: UserEntity,
    @ManyToOne
    @JoinColumn(name = "EXECUTOR_ID")
    var executor: UserEntity? = null,
    @ManyToOne
    @JoinColumn(name = "LOCATION_ID")
    var location: LocationEntity,
    @OneToMany(
        mappedBy = "commission",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE],
        orphanRemoval = true
    )
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
    var acceptedOrdered: Int? = null,
    var recruited: Int? = null,
    var startDate: LocalDate,
    var acceptedStartDate: LocalDate? = null,
    var endDate: LocalDate?,
    var wageRateMin: Float,
    var wageRateMax: Float,
    @Convert(converter = SettlementTypeEnumConverterImpl::class)
    var settlementType: SettlementType,
    var description: String?,
    @Convert(converter = CommissionRecordStatusEnumConverterImpl::class)
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