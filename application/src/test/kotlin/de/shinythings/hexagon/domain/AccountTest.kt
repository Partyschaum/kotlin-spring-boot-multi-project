package de.shinythings.hexagon.domain

import de.shinythings.hexagon.common.AccountTestData.defaultAccount
import de.shinythings.hexagon.common.ActivityTestData.defaultActivity
import de.shinythings.hexagon.domain.Account.AccountId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AccountTest {

    @Test
    fun `calculates the balance`() {
        val accountId = AccountId(1)

        val account = defaultAccount()
                .withAccountId(accountId)
                .withBaselineBalance(Money.of(555))
                .withActivityWindow(ActivityWindow(
                        defaultActivity()
                                .withTargetAccount(accountId)
                                .withMoney(Money.of(999))
                                .build(),
                        defaultActivity()
                                .withTargetAccount(accountId)
                                .withMoney(Money.of(1))
                                .build()
                ))
                .build()

        val balance = account.calculateBalance()

        assertThat(balance).isEqualTo(Money.of(1555))
    }

    @Test
    fun `withdrawal succeeds`() {
        val accountId = AccountId(1)

        val account = defaultAccount()
                .withAccountId(accountId)
                .withBaselineBalance(Money.of(555))
                .withActivityWindow(ActivityWindow(
                        defaultActivity()
                                .withTargetAccount(accountId)
                                .withMoney(Money.of(999))
                                .build(),
                        defaultActivity()
                                .withTargetAccount(accountId)
                                .withMoney(Money.of(1))
                                .build()
                ))
                .build()

        val success = account.withdraw(
                money = Money.of(555),
                targetAccountId = AccountId(99)
        )

        assertThat(success).isTrue()
        assertThat(account.activityWindow.activities()).hasSize(3)
        assertThat(account.calculateBalance()).isEqualTo(Money.of(1000))
    }

    @Test
    fun `withdrawal fails`() {
        val accountId = AccountId(1)

        val account = defaultAccount()
                .withAccountId(accountId)
                .withBaselineBalance(Money.of(555))
                .withActivityWindow(ActivityWindow(
                        defaultActivity()
                                .withTargetAccount(accountId)
                                .withMoney(Money.of(999))
                                .build(),
                        defaultActivity()
                                .withTargetAccount(accountId)
                                .withMoney(Money.of(1))
                                .build()
                ))
                .build()

        val success = account.withdraw(
                money = Money.of(1556),
                targetAccountId = AccountId(99)
        )

        assertThat(success).isFalse()
        assertThat(account.activityWindow.activities()).hasSize(2)
        assertThat(account.calculateBalance()).isEqualTo(Money.of(1555))
    }

    @Test
    fun `deposit succeeds`() {
        val accountId = AccountId(1)

        val account = defaultAccount()
                .withAccountId(accountId)
                .withBaselineBalance(Money.of(555))
                .withActivityWindow(ActivityWindow(
                        defaultActivity()
                                .withTargetAccount(accountId)
                                .withMoney(Money.of(999))
                                .build(),
                        defaultActivity()
                                .withTargetAccount(accountId)
                                .withMoney(Money.of(1))
                                .build()
                ))
                .build()

        val success = account.deposit(
                money = Money.of(445),
                sourceAccountId = AccountId(99)
        )

        assertThat(success).isTrue()
        assertThat(account.activityWindow.activities()).hasSize(3)
        assertThat(account.calculateBalance()).isEqualTo(Money.of(2000))
    }
}
