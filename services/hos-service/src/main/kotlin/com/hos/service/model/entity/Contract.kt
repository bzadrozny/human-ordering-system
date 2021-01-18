package com.hos.service.model.entity

import com.hos.service.converter.jpa.impl.ContractTypeEnumConverterImpl
import com.hos.service.converter.jpa.impl.SettlementTypeEnumConverterImpl
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
        var code: String,
        var contractDate: LocalDate,
        var startDate: LocalDate,
        var endDate: LocalDate?,
        var salary: Float,
        @Convert(converter = ContractTypeEnumConverterImpl::class)
        var contractType: ContractType,
        var description: String?,
        var approved: Boolean? = false,
        @ManyToOne
        @JoinColumn(name = "RECRUITER_ID")
        val recruiter: UserEntity,
        @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "CANDIDATE_ID")
        var candidate: CandidateEntity? = null,
        @ManyToOne
        @JoinColumn(name = "COMMISSION_RECORD_ID")
        val commissionRecord: CommissionRecordEntity,
        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "HISTORY_LOG_ID")
        val history: HistoryLogEntity = HistoryLogEntity()
) : Serializable