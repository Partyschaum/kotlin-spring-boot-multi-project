package de.shinythings.hexagon.application.port.output

import de.shinythings.hexagon.domain.Account.AccountId

interface AccountLockPort {

    fun lockAccount(accountId: AccountId)

    fun releaseAccount(accountId: AccountId)
}
