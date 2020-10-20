package de.shinythings.hexagon.application.usecase

import de.shinythings.hexagon.application.port.input.GetAccountBalancePort.GetAccountBalanceQuery
import de.shinythings.hexagon.application.port.input.GetAccountBalancePort.GetAccountBalanceResponse
import de.shinythings.hexagon.application.port.output.LoadAccountPort
import de.shinythings.hexagon.domain.Account
import de.shinythings.hexagon.domain.Account.AccountId
import de.shinythings.hexagon.domain.Money
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class GetAccountBalanceUseCaseTest {

    @MockK
    private lateinit var loadAccountPort: LoadAccountPort

    @Test
    fun `returns the balance of the account`() {

        val accountId = AccountId(23)

        val account = givenAnAccountWithId(accountId)
        givenAccountHasBalance(account, Money.of(10))

        val getAccountBalanceUseCase = GetAccountBalanceUseCase(
                loadAccountPort = loadAccountPort
        )

        val response = getAccountBalanceUseCase(
                GetAccountBalanceQuery(
                        accountId = accountId
                )
        )

        assertThat(response).isEqualTo(
                GetAccountBalanceResponse(
                        money = Money.of(10)
                )
        )
    }

    private fun givenAccountHasBalance(account: Account, money: Money) {
        every { account.calculateBalance() } returns money
    }

    private fun givenAnAccountWithId(accountId: AccountId): Account {
        val account = mockk<Account>()

        every { account.id } returns accountId

        every { loadAccountPort.loadAccountOrNull(eq(accountId), ofType(LocalDateTime::class)) } returns account

        return account
    }
}
