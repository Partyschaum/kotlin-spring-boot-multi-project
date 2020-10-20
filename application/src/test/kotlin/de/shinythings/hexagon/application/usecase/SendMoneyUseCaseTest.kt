package de.shinythings.hexagon.application.usecase

import de.shinythings.hexagon.application.MoneyTransferProperties
import de.shinythings.hexagon.application.port.input.SendMoneyPort.SendMoneyCommand
import de.shinythings.hexagon.application.port.output.AccountLockPort
import de.shinythings.hexagon.application.port.output.LoadAccountPort
import de.shinythings.hexagon.application.port.output.UpdateAccountPort
import de.shinythings.hexagon.domain.Account
import de.shinythings.hexagon.domain.Account.AccountId
import de.shinythings.hexagon.domain.Money
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class SendMoneyUseCaseTest {

    @MockK
    private lateinit var loadAccountPort: LoadAccountPort

    @RelaxedMockK
    private lateinit var accountLockPort: AccountLockPort

    @RelaxedMockK
    private lateinit var updateAccountPort: UpdateAccountPort

    private val sendMoneyUseCase: SendMoneyUseCase by lazy {
        SendMoneyUseCase(
                loadAccountPort = loadAccountPort,
                updateAccountPort = updateAccountPort,
                accountLockPort = accountLockPort,
                moneyTransferProperties = moneyTransferProperties()
        )
    }

    @Test
    fun `given withdrawal fails then only source account is locked and released`() {

        val sourceAccountId = AccountId(41)
        val sourceAccount = givenAnAccountWithId(sourceAccountId)

        val targetAccountId = AccountId(42)
        val targetAccount = givenAnAccountWithId(targetAccountId)

        givenWithdrawalWillFail(sourceAccount)
        givenDepositWillSucceed(targetAccount)

        val command = SendMoneyCommand(
                sourceAccountId = sourceAccountId,
                targetAccountId = targetAccountId,
                money = Money.of(300)
        )

        sendMoneyUseCase(command)

        verifySequence {
            accountLockPort.lockAccount(eq(sourceAccountId))
            accountLockPort.releaseAccount(eq(sourceAccountId))
        }
    }

    @Test
    fun `transaction succeeds`() {

        val sourceAccount = givenSourceAccount()
        val targetAccount = givenTargetAccount()

        val sourceAccountId = sourceAccount.id
        val targetAccountId = targetAccount.id

        givenWithdrawalWillSucceed(sourceAccount)
        givenDepositWillSucceed(targetAccount)

        val money = Money.of(500)


        val command = SendMoneyCommand(
                sourceAccountId = sourceAccountId,
                targetAccountId = targetAccountId,
                money = money
        )

        sendMoneyUseCase(command)

        verifyOrder {
            accountLockPort.lockAccount(eq(sourceAccountId))
            sourceAccount.withdraw(eq(money), eq(targetAccountId))

            accountLockPort.lockAccount(eq(targetAccountId))
            targetAccount.deposit(eq(money), eq(sourceAccountId))

            accountLockPort.releaseAccount(eq(sourceAccountId))
            accountLockPort.releaseAccount(eq(targetAccountId))
        }

        confirmVerified(accountLockPort)

        thenAccountsHaveBeenUpdated(sourceAccountId, targetAccountId)
    }

    private fun givenAnAccountWithId(accountId: AccountId): Account {
        val account = mockk<Account>()

        every { account.id } returns accountId

        every { loadAccountPort.loadAccountOrNull(eq(accountId), ofType(LocalDateTime::class)) } returns account

        return account
    }

    private fun givenTargetAccount() = givenAnAccountWithId(AccountId(42))

    private fun givenSourceAccount() = givenAnAccountWithId(AccountId(41))

    private fun thenAccountsHaveBeenUpdated(vararg accountIds: AccountId) {
        val capturedAccounts = mutableListOf<Account>()

        every { updateAccountPort.updateActivities(capture(capturedAccounts)) }

        assertThat(capturedAccounts.all { capturedAccount -> accountIds.any { id -> id == capturedAccount.id } }).isTrue()

        verify(exactly = accountIds.size) { updateAccountPort.updateActivities(ofType(Account::class)) }

        confirmVerified(updateAccountPort)
    }

    private fun givenDepositWillSucceed(account: Account) {
        every { account.deposit(ofType(Money::class), ofType(AccountId::class)) } returns true
    }

    private fun givenWithdrawalWillSucceed(account: Account) {
        every { account.withdraw(ofType(Money::class), ofType(AccountId::class)) } returns true
    }

    private fun givenWithdrawalWillFail(account: Account) {
        every { account.withdraw(ofType(Money::class), ofType(AccountId::class)) } returns false
    }

    private fun moneyTransferProperties() = MoneyTransferProperties(
            maximumTransferThreshold = Money.of(Long.MAX_VALUE)
    )
}
