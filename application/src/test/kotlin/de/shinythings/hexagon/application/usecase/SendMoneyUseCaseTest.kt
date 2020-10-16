package de.shinythings.hexagon.application.usecase

import de.shinythings.hexagon.application.MoneyTransferProperties
import de.shinythings.hexagon.application.NoOpAccountLock
import de.shinythings.hexagon.application.port.input.SendMoneyPort.SendMoneyCommand
import de.shinythings.hexagon.application.port.out.LoadAccountPort
import de.shinythings.hexagon.application.port.out.UpdateAccountPort
import de.shinythings.hexagon.domain.Account
import de.shinythings.hexagon.domain.Account.AccountId
import de.shinythings.hexagon.domain.ActivityWindow
import de.shinythings.hexagon.domain.Money
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import java.time.LocalDateTime

class SendMoneyUseCaseTest : DescribeSpec() {

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

                val loadAccount = LoadAccountDummyAdapter(
                        mapOf(
                                sourceAccountId to sourceAccount,
                                targetAccountId to targetAccount
                        )
                )

                val updateAccount = UpdateAccountDummyAdapter()

                val sendMoney = SendMoneyUseCase(
                        loadAccountPort = loadAccount,
                        updateAccountPort = updateAccount,
                        accountLockPort = NoOpAccountLock(),
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

    private class LoadAccountDummyAdapter(
            private val accounts: Map<AccountId, Account>
    ) : LoadAccountPort {

        override fun loadAccount(accountId: AccountId, baselineDate: LocalDateTime) = accounts[accountId]
    }

    private class UpdateAccountDummyAdapter() : UpdateAccountPort {

        var updatedAccounts = mutableListOf<Account>()

        override fun updateActivities(account: Account) {
            updatedAccounts.add(account)
        }
    }
}
