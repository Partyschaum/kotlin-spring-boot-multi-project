package de.shinythings.hexagon.domain

import java.time.LocalDateTime

class Account(
        val id: AccountId,
        val baselineBalance: Money,
        val activityWindow: ActivityWindow
) {

    data class AccountId(val value: Long)

    fun calculateBalance() = Money.add(
            baselineBalance,
            activityWindow.calculateBalance(id)
    )

    fun withdraw(money: Money, targetAccountId: AccountId): Boolean {

        if (!mayWithdraw(money)) {
            return false
        }

        val withdrawal = Activity(
                ownerAccountId = id,
                sourceAccountId = id,
                targetAccountId = targetAccountId,
                timestamp = LocalDateTime.now(),
                money = money
        )

        activityWindow.addActivity(withdrawal)

        return true
    }

    private fun mayWithdraw(money: Money): Boolean =
            Money.add(calculateBalance(), money.negate())
                    .isPositiveOrZero()

    fun deposit(money: Money, sourceAccountId: AccountId): Boolean {
        val deposit = Activity(
                ownerAccountId = id,
                sourceAccountId = sourceAccountId,
                targetAccountId = id,
                timestamp = LocalDateTime.now(),
                money = money
        )

        activityWindow.addActivity(deposit)

        return true
    }
}
