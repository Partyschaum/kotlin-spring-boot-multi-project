package de.shinythings.hexagon.application

import de.shinythings.hexagon.application.port.out.AccountLock
import de.shinythings.hexagon.domain.Account
import org.springframework.stereotype.Component

@Component
class NoOpAccountLock : AccountLock {

    override fun lockAccount(accountId: Account.AccountId) {
        // do nothing
    }

    override fun releaseAccount(accountId: Account.AccountId) {
        // do nothing
    }
}
