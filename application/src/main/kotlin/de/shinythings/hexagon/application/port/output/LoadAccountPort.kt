package de.shinythings.hexagon.application.port.output

import de.shinythings.hexagon.domain.Account
import de.shinythings.hexagon.domain.Account.AccountId
import java.time.LocalDateTime

interface LoadAccountPort {

    fun loadAccountOrNull(accountId: AccountId, baselineDate: LocalDateTime): Account?

    class AccountNotFoundException(accountId: AccountId) : RuntimeException(
            "No account was found for account ID '$accountId'"
    )
}

