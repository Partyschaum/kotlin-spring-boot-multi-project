package de.shinythings.hexagon.application.usecase

import de.shinythings.hexagon.application.port.input.SendMoney.SendMoneyCommand
import de.shinythings.hexagon.application.port.out.LoadAccount
import de.shinythings.hexagon.application.port.out.UpdateAccount
import de.shinythings.hexagon.domain.Account
import de.shinythings.hexagon.domain.Account.AccountId
import de.shinythings.hexagon.domain.ActivityWindow
import de.shinythings.hexagon.domain.Money
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class SendMoneyTest : DescribeSpec({

    describe("SendMoney") {
        it("sends money") {
            val dummyAccount = Account(
                    id = AccountId(42),
                    baselineBalance = Money.of(10),
                    activityWindow = ActivityWindow(
                            activities = mutableListOf()
                    )
            )

            val loadAccount = LoadAccountDummy(dummyAccount)

            val updateAccount = UpdateAccountDummy()

            val sendMoney = SendMoney(
                    loadAccount = loadAccount,
                    updateAccount = updateAccount
            )

            sendMoney(
                    SendMoneyCommand(
                            sourceAccountId = AccountId(23),
                            targetAccountId = AccountId(42),
                            money = Money.of(15)
                    )
            )

            updateAccount.updateActivitiesWasCalled shouldBe true
        }
    }
})

class LoadAccountDummy(private val account: Account) : LoadAccount {

    override fun loadAccount(accountId: AccountId, baselineDate: LocalDateTime) = account
}

class UpdateAccountDummy() : UpdateAccount {

    var updateActivitiesWasCalled = false

    override fun updateActivities(account: Account) {
        updateActivitiesWasCalled = true
    }
}

