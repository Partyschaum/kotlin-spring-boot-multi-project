package de.shinythings.hexagon.domain

import java.time.LocalDateTime

data class Activity(
        val id: ActivityId?,
        val ownerAccountId: Account.AccountId,
        val sourceAccountId: Account.AccountId,
        val targetAccountId: Account.AccountId,
        val timestamp: LocalDateTime,
        val money: Money
) {
    data class ActivityId(val value: Long)
}
