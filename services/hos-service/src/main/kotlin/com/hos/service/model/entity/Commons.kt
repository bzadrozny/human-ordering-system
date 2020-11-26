package com.hos.service.model.entity

import com.hos.service.model.enum.ActionType
import com.hos.service.security.UserDetailsContainer
import org.springframework.security.core.context.SecurityContextHolder
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "HISTORY_LOG")
class HistoryLogEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HistoryLogSeqGen")
        @SequenceGenerator(name = "HistoryLogSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1
) : Serializable {

    @JoinColumn(name = "CREATOR_ID")
    var creator: Long? = null
    var creationDate: LocalDate? = null
    var creationTime: LocalTime? = null

    @JoinColumn(name = "MODIFIER_ID")
    var modifier: Long? = null
    var modificationDate: LocalDate? = null
    var modificationTime: LocalTime? = null

    var lastAction: ActionType? = null

    @PrePersist
    fun prePersist() {
        val principal = SecurityContextHolder.getContext()?.authentication?.principal
        creator = principal?.let { (it as UserDetailsContainer).id } ?: 0
        creationDate = LocalDate.now()
        creationTime = LocalTime.now()
        lastAction = ActionType.CREATE
    }

    @PreUpdate
    fun preUpdate() {
        val principal = SecurityContextHolder.getContext()?.authentication?.principal
        modifier = principal?.let { (it as UserDetailsContainer).id } ?: 0
        modificationDate = LocalDate.now()
        modificationTime = LocalTime.now()
        lastAction = ActionType.UPDATE
    }

}

@Entity(name = "ADDRESS")
class AddressEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AddressSeqGen")
        @SequenceGenerator(name = "AddressSeqGen", initialValue = 1000, allocationSize = 10)
        val id: Long = -1,
        @Version
        val version: Int = 0,
        @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        @JoinColumn(name = "HISTORY_LOG_ID")
        val history: HistoryLogEntity = HistoryLogEntity(),
        var street: String,
        var number: String,
        var city: String,
        var postalCode: String,
) : Serializable
