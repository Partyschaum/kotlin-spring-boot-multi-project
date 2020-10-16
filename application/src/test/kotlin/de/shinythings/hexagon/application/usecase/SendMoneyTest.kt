package de.shinythings.hexagon.application.usecase

import de.shinythings.hexagon.application.MoneyTransferProperties
import de.shinythings.hexagon.application.NoOpAccountLock
import de.shinythings.hexagon.application.port.input.SendMoney.SendMoneyCommand
import de.shinythings.hexagon.application.port.out.LoadAccount
import de.shinythings.hexagon.application.port.out.UpdateAccount
import de.shinythings.hexagon.domain.Account
import de.shinythings.hexagon.domain.Account.AccountId
import de.shinythings.hexagon.domain.ActivityWindow
import de.shinythings.hexagon.domain.Money
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import java.time.LocalDateTime

class SendMoneyTest : DescribeSpec() {

    init {
        describe("SendMoney") {
            it("updates the source and target accounts") {
                val sourceAccountId = AccountId(42)
                val sourceAccount = Account(
                        id = sourceAccountId,
                        baselineBalance = Money.of(20),
                        activityWindow = ActivityWindow(
                                activities = mutableListOf()
                        )

                )
                val targetAccountId = AccountId(23)
                val targetAccount = Account(
                        id = targetAccountId,
                        baselineBalance = Money.of(10),
                        activityWindow = ActivityWindow(
                                activities = mutableListOf()
                        )
                )

                val loadAccount = LoadAccountDummy(
                        mapOf(
                                sourceAccountId to sourceAccount,
                                targetAccountId to targetAccount
                        )
                )

                val updateAccount = UpdateAccountDummy()

                val sendMoney = SendMoney(
                        loadAccount = loadAccount,
                        updateAccount = updateAccount,
                        accountLock = NoOpAccountLock(),
                        moneyTransferProperties = MoneyTransferProperties()
                )

                sendMoney(
                        SendMoneyCommand(
                                sourceAccountId = sourceAccountId,
                                targetAccountId = targetAccountId,
                                money = Money.of(15)
                        )
                )

                updateAccount.updatedAccounts shouldContain sourceAccount
                updateAccount.updatedAccounts shouldContain targetAccount
            }
        }
    }

    private class LoadAccountDummy(
            private val accounts: Map<AccountId, Account>
    ) : LoadAccount {

        override fun loadAccount(accountId: AccountId, baselineDate: LocalDateTime) = accounts[accountId]
    }

    private class UpdateAccountDummy() : UpdateAccount {

        var updatedAccounts = mutableListOf<Account>()

        override fun updateActivities(account: Account) {
            updatedAccounts.add(account)
        }
    }
}
