package de.shinythings.hexagon.adapter.persistence

import de.shinythings.hexagon.adapter.persistence.account.AccountMapper
import de.shinythings.hexagon.adapter.persistence.account.AccountRepository
import de.shinythings.hexagon.adapter.persistence.activity.ActivityRepository
import de.shinythings.hexagon.application.port.output.LoadAccountPort
import de.shinythings.hexagon.application.port.output.UpdateAccountPort
import de.shinythings.hexagon.common.PersistenceAdapter
import de.shinythings.hexagon.domain.Account
import de.shinythings.hexagon.domain.Account.AccountId
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException

@PersistenceAdapter
class AccountPersistenceAdapter(
        private val accountRepository: AccountRepository,
        private val activityRepository: ActivityRepository,
        private val accountMapper: AccountMapper
) : LoadAccountPort, UpdateAccountPort {

    override fun loadAccountOrNull(accountId: AccountId, baselineDate: LocalDateTime): Account? {

        val account = accountRepository.findByIdOrNull(accountId.value)
                ?: throw EntityNotFoundException()

        val activities = activityRepository.findByOwnerSince(accountId.value, baselineDate)

        val withdrawalBalance = activityRepository.withdrawalBalanceUntil(accountId.value, baselineDate).orZero()

        val depositBalance = activityRepository.depositBalanceUntil(accountId.value, baselineDate).orZero()

        return accountMapper.mapToDomainEntity(
                account = account,
                activities = activities,
                withdrawalBalance = withdrawalBalance,
                depositBalance = depositBalance
        )
    }

    override fun updateActivities(account: Account) {
        account.activityWindow.activities()
                .map(accountMapper::mapToJpaEntity)
                .forEach(activityRepository::save)
    }
}

private fun Long?.orZero() = this ?: 0
