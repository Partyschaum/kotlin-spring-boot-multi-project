package de.shinythings.hexagon.adapter.persistence.account

import de.shinythings.hexagon.adapter.persistence.activity.ActivityEntity
import de.shinythings.hexagon.domain.Account
import de.shinythings.hexagon.domain.Account.AccountId
import de.shinythings.hexagon.domain.Activity
import de.shinythings.hexagon.domain.Activity.ActivityId
import de.shinythings.hexagon.domain.ActivityWindow
import de.shinythings.hexagon.domain.Money
import org.springframework.stereotype.Component

@Component
class AccountMapper {

    fun mapToDomainEntity(
            account: AccountEntity,
            activities: List<ActivityEntity>,
            withdrawalBalance: Long,
            depositBalance: Long
    ): Account {

        val baselineBalance = Money.subtract(
                Money.of(depositBalance),
                Money.of(withdrawalBalance)
        )

        check(account.id != null)

        return Account(
                id = AccountId(account.id),
                baselineBalance = baselineBalance,
                activityWindow = mapToActivityWindow(activities)
        )
    }

    fun mapToJpaEntity(activity: Activity): ActivityEntity {
        return ActivityEntity(
                id = activity.id?.value,
                timestamp = activity.timestamp,
                ownerAccountId = activity.ownerAccountId.value,
                sourceAccountId = activity.sourceAccountId.value,
                targetAccountId = activity.targetAccountId.value,
                amount = activity.money.amount.toLong()
        )
    }

    private fun mapToActivityWindow(activities: List<ActivityEntity>): ActivityWindow {
        return ActivityWindow(
                activities = activities.map {
                    check(it.id != null)

                    Activity(
                            id = ActivityId(it.id),
                            ownerAccountId = AccountId(it.ownerAccountId),
                            sourceAccountId = AccountId(it.sourceAccountId),
                            targetAccountId = AccountId(it.targetAccountId),
                            timestamp = it.timestamp,
                            money = Money.of(it.amount)
                    )
                }.toMutableList()
        )
    }
}
