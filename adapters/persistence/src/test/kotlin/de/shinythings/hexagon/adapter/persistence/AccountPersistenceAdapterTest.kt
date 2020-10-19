package de.shinythings.hexagon.adapter.persistence

import de.shinythings.hexagon.adapter.persistence.account.AccountMapper
import de.shinythings.hexagon.adapter.persistence.activity.ActivityRepository
import de.shinythings.hexagon.common.AccountTestData.defaultAccount
import de.shinythings.hexagon.common.ActivityTestData.defaultActivity
import de.shinythings.hexagon.domain.Account.AccountId
import de.shinythings.hexagon.domain.ActivityWindow
import de.shinythings.hexagon.domain.Money
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.jdbc.Sql
import java.time.LocalDateTime

@DataJpaTest
@Import(AccountPersistenceAdapter::class, AccountMapper::class)
class AccountPersistenceAdapterTest(
        @Autowired val accountPersistenceAdapter: AccountPersistenceAdapter,
        @Autowired val activityRepository: ActivityRepository
) {

    @Test
    @Sql("classpath:de.shinythings.hexagon.adapter.persistence/ActivityRepositoryTest.sql")
    fun `loads the account`() {
        val account = accountPersistenceAdapter.loadAccountOrNull(
                accountId = AccountId(1),
                baselineDate = LocalDateTime.of(2018, 8, 10, 0, 0)
        )!!

        assertThat(account.activityWindow.activities()).hasSize(2)
        assertThat(account.calculateBalance()).isEqualTo(Money.of(500))
    }

    @Test
    fun `updates the activities`() {
        val account = defaultAccount()
                .withBaselineBalance(Money.of(555))
                .withActivityWindow(ActivityWindow(
                        defaultActivity()
                                .withId(null)
                                .withMoney(Money.of(1))
                                .build()
                ))
                .build()

        accountPersistenceAdapter.updateActivities(account)

        assertThat(activityRepository.count()).isEqualTo(1)

        val savedActivity = activityRepository.findAll()[0]

        assertThat(savedActivity.amount).isEqualTo(1)
    }
}
