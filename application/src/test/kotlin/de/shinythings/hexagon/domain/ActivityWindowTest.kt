package de.shinythings.hexagon.domain

import de.shinythings.hexagon.common.ActivityTestData.defaultActivity
import de.shinythings.hexagon.domain.Account.AccountId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ActivityWindowTest {

    @Test
    fun `calculates the start timestamp`() {
        val window = ActivityWindow(
                defaultActivity().withTimestamp(startDate()).build(),
                defaultActivity().withTimestamp(inBetweenDate()).build(),
                defaultActivity().withTimestamp(endDate()).build()
        )

        assertThat(window.startTimestamp()).isEqualTo((startDate()))
    }

    @Test
    fun `calculates the end timestamp`() {
        val window = ActivityWindow(
                defaultActivity().withTimestamp(startDate()).build(),
                defaultActivity().withTimestamp(inBetweenDate()).build(),
                defaultActivity().withTimestamp(endDate()).build()
        )

        assertThat(window.endTimestamp()).isEqualTo((endDate()))
    }

    @Test
    fun `calculates the balance`() {
        val accountId1 = AccountId(1)
        val accountId2 = AccountId(2)

        val window = ActivityWindow(
                defaultActivity()
                        .withSourceAccount(accountId1)
                        .withTargetAccount(accountId2)
                        .withMoney(Money.of(999))
                        .build(),
                defaultActivity()
                        .withSourceAccount(accountId1)
                        .withTargetAccount(accountId2)
                        .withMoney(Money.of(1))
                        .build(),
                defaultActivity()
                        .withSourceAccount(accountId2)
                        .withTargetAccount(accountId1)
                        .withMoney(Money.of(500))
                        .build()
        )

        assertThat(window.calculateBalance(accountId1)).isEqualTo(Money.of(-500))
        assertThat(window.calculateBalance(accountId2)).isEqualTo(Money.of(500))
    }

    private fun startDate() = LocalDateTime.of(2019, 8, 3, 0, 0)

    private fun inBetweenDate() = LocalDateTime.of(2019, 8, 4, 0, 0)

    private fun endDate() = LocalDateTime.of(2019, 8, 5, 0, 0)
}
