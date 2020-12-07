package com.hos.service.model.entity

import com.hos.service.model.enum.ContractType
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*

@Entity(name = "CONTRACT")
class ContractEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ContractSeqGen")
        @SequenceGenerator(name = "ContractSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1,
        @Version
        val version: Int = 0,
        val code: String,
        var contractDate: LocalDate,
        var startDate: LocalDate,
        var endDate: LocalDate,
        var salary: Float,
        var contractType: ContractType,
        var description: String,
        var approved: Boolean,
        @ManyToOne
        @JoinColumn(name = "RECRUITER_ID")
        val recruiter: UserEntity,
        @ManyToOne
        @JoinColumn(name = "CANDIDATE_ID")
        val candidate: CandidateEntity? = null,
        @ManyToOne
        @JoinColumn(name = "COMMISSION_RECORD_ID")
        val commissionRecord: CommissionRecordEntity,
        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "HISTORY_LOG_ID")
        val history: HistoryLogEntity = HistoryLogEntity()
) : Serializable