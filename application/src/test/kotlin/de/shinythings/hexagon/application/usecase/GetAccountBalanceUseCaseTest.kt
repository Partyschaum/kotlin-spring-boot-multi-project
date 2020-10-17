package de.shinythings.hexagon.application.usecase

import de.shinythings.hexagon.application.port.input.GetAccountBalancePort.GetAccountBalanceQuery
import de.shinythings.hexagon.application.port.input.GetAccountBalancePort.GetAccountBalanceResponse
import de.shinythings.hexagon.application.port.output.LoadAccountPort
import de.shinythings.hexagon.domain.Account
import de.shinythings.hexagon.domain.Account.AccountId
import de.shinythings.hexagon.domain.ActivityWindow
import de.shinythings.hexagon.domain.Money
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class GetAccountBalanceUseCaseTest : DescribeSpec() {

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

                val loadAccount = LoadAccountDummyAdapter(dummyAccount)

                val getAccountBalance = GetAccountBalanceUseCase(
                        loadAccountPort = loadAccount
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

    private class LoadAccountDummyAdapter(private val account: Account) : LoadAccountPort {

        override fun loadAccount(accountId: AccountId, baselineDate: LocalDateTime) = account
    }
}
