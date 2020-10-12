package de.shinythings.hexagon.application.usecase

import de.shinythings.hexagon.application.port.input.GetAccountBalance.GetAccountBalanceQuery
import de.shinythings.hexagon.application.port.input.GetAccountBalance.GetAccountBalanceResponse
import de.shinythings.hexagon.application.port.out.LoadAccount
import de.shinythings.hexagon.domain.Account
import de.shinythings.hexagon.domain.Account.AccountId
import de.shinythings.hexagon.domain.ActivityWindow
import de.shinythings.hexagon.domain.Money
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class GetAccountBalanceTest : DescribeSpec() {

    init {
        describe("GetAccountsBalance") {
            it("gets the account's balance") {
                val dummyAccount = Account(
                        id = AccountId(42),
                        baselineBalance = Money.of(10),
                        activityWindow = ActivityWindow(
                                activities = mutableListOf()
                        )
                )

                val loadAccount = LoadAccountDummy(dummyAccount)

                val getAccountBalance = GetAccountBalance(
                        loadAccount = loadAccount
                )

                val response = getAccountBalance(
                        GetAccountBalanceQuery(
                                accountId = AccountId(42)
                        )
                )

                response shouldBe GetAccountBalanceResponse(
                        money = Money.of(10)
                )
            }
        }
    }

    private class LoadAccountDummy(private val account: Account) : LoadAccount {

        override fun loadAccount(accountId: AccountId, baselineDate: LocalDateTime) = account
    }
}
