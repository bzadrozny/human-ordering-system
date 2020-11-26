package com.hos.service.model.entity

import com.hos.service.model.enum.ContractType
import com.hos.service.model.enum.OrderRecordStatus
import com.hos.service.model.enum.OrderStatus
import com.hos.service.model.enum.SettlementType
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
        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "HISTORY_LOG_ID")
        val history: HistoryLogEntity = HistoryLogEntity(),
        @OneToMany(mappedBy = "commission")
        val recordEntities: MutableSet<OrderRecordEntity> = mutableSetOf(),
        @ManyToOne
        @JoinColumn(name = "PRINCIPAL_ID")
        var principal: UserEntity,
        @ManyToOne
        @JoinColumn(name = "EXECUTOR_ID")
        var executor: UserEntity,
        @ManyToOne
        @JoinColumn(name = "LOCATION_ID")
        var location: LocationEntity,
        var description: String,
        var status: OrderStatus
) : Serializable

@Entity(name = "COMMISSION_RECORD")
class OrderRecordEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CommissionRecSeqGen")
        @SequenceGenerator(name = "CommissionRecSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1,
        @Version
        val version: Int = 0,
        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "HISTORY_LOG_ID")
        val history: HistoryLogEntity = HistoryLogEntity(),
        @ManyToOne
        @JoinColumn(name = "COMMISSION_ID")
        val commission: CommissionEntity,
        var jobName: String,
        var ordered: Int,
        var accepted: Int,
        var recruited: Int,
        var startDate: LocalDate,
        var endDate: LocalDate,
        var wageRateMin: Float,
        var wageRateMax: Float,
        var settlementType: SettlementType,
        var status: OrderRecordStatus,
        var description: String
) : Serializable

@Entity(name = "CONTRACT")
class ContractEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ContractSeqGen")
        @SequenceGenerator(name = "ContractSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1,
        @Version
        val version: Int = 0,
        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "HISTORY_LOG_ID")
        val history: HistoryLogEntity = HistoryLogEntity(),
        @ManyToOne
        @JoinColumn(name = "COMMISSION_RECORD_ID")
        val commissionRecord: OrderRecordEntity,
        @ManyToOne
        @JoinColumn(name = "RECRUITER_ID")
        val recruiter: UserEntity,
        @ManyToOne
        @JoinColumn(name = "CANDIDATE_ID")
        val candidate: CandidateEntity,
        var contractDate: LocalDate,
        var startDate: LocalDate,
        var endDate: LocalDate,
        var salary: Float,
        var contractType: ContractType,
        var description: String
) : Serializable