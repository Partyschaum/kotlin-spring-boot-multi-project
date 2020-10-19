package de.shinythings.hexagon.common

import de.shinythings.hexagon.domain.Account
import de.shinythings.hexagon.domain.Account.AccountId
import de.shinythings.hexagon.domain.ActivityWindow
import de.shinythings.hexagon.domain.Money

object AccountTestData {

    fun defaultAccount(): AccountBuilder {
        return AccountBuilder()
                .withAccountId(AccountId(42L))
                .withBaselineBalance(Money.of(999L))
                .withActivityWindow(ActivityWindow(
                        ActivityTestData.defaultActivity().build(),
                        ActivityTestData.defaultActivity().build()
                ))
    }

    class AccountBuilder {

        private var accountId: AccountId? = null
        private var baselineBalance: Money? = null
        private var activityWindow: ActivityWindow? = null

        fun withAccountId(accountId: AccountId?): AccountBuilder {
            this.accountId = accountId
            return this
        }

        fun withBaselineBalance(baselineBalance: Money?): AccountBuilder {
            this.baselineBalance = baselineBalance
            return this
        }

        fun withActivityWindow(activityWindow: ActivityWindow?): AccountBuilder {
            this.activityWindow = activityWindow
            return this
        }

        fun build(): Account {
            val accountId = accountId
            val baselineBalance = baselineBalance
            val activityWindow = activityWindow

            checkNotNull(accountId)
            checkNotNull(baselineBalance)
            checkNotNull(activityWindow)

            return Account(accountId, baselineBalance, activityWindow)
        }
    }
}
