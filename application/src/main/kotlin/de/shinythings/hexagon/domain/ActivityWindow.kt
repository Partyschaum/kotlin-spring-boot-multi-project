package de.shinythings.hexagon.domain

import de.shinythings.hexagon.domain.Account.AccountId

class ActivityWindow(private val activities: MutableList<Activity>) {

    constructor(vararg activity: Activity) : this(activity.toMutableList())

    fun startTimestamp() = activities.minOfOrNull { it.timestamp }
            ?: throw IllegalStateException("ActivityWindow has not activities yet!")

    fun endTimestamp() = activities.maxOfOrNull { it.timestamp }
            ?: throw IllegalStateException("ActivityWindow has not activities yet!")

    fun calculateBalance(accountId: AccountId): Money {
        val depositBalance = activities
                .filter { it.targetAccountId == accountId }
                .map { it.money }
                .fold(Money.ZERO) { acc, money -> Money.add(acc, money) }

        val withdrawalBalance = activities
                .filter { it.sourceAccountId == accountId }
                .map { it.money }
                .fold(Money.ZERO) { acc, money -> Money.add(acc, money) }

        return Money.add(depositBalance, withdrawalBalance.negate())
    }

    fun activities() = activities.toList()

    fun addActivity(activity: Activity) = activities.add(activity)
}
