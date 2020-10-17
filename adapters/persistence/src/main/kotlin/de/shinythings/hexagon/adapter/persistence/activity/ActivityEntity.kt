package de.shinythings.hexagon.adapter.persistence.activity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "activity")
data class ActivityEntity(

        @Id
        @GeneratedValue
        val id: Long,

        @Column val timestamp: LocalDateTime,
        @Column val ownerAccountId: Long,
        @Column val sourceAccountId: Long,
        @Column val targetAccountId: Long,
        @Column val amount: Long
)
