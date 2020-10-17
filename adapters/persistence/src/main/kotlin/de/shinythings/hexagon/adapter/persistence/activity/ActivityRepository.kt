package de.shinythings.hexagon.adapter.persistence.activity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface ActivityRepository : JpaRepository<ActivityEntity, Long> {

    @Query("""
        select a from ActivityEntity a
        where a.ownerAccountId = :ownerAccountId
        and a.timestamp >= :since
    """)
    fun findByOwnerSince(
            @Param("ownerAccountId") ownerAccountId: Long,
            @Param("since") since: LocalDateTime
    ): List<ActivityEntity>

    @Query("""
        select sum(a.amount) from ActivityEntity a
        where a.targetAccountId = :accountId
        and a.ownerAccountId = :accountId
        and a.timestamp < :until
    """)
    fun depositBalanceUntil(
            @Param("accountId") accountId: Long,
            @Param("until") until: LocalDateTime
    ): Long?

    @Query("""
        select sum(a.amount) from ActivityEntity a
        where a.sourceAccountId = :accountId
        and a.ownerAccountId= :accountId
        and a.timestamp < :until
    """)
    fun withdrawalBalanceUntil(
            @Param("accountId") accountId: Long,
            @Param("until") until: LocalDateTime
    ): Long?
}
