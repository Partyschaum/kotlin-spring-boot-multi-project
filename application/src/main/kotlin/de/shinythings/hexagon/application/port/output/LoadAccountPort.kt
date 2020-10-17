package de.shinythings.hexagon.application.port.output

import de.shinythings.hexagon.domain.Account
import de.shinythings.hexagon.domain.Account.AccountId
import java.time.LocalDateTime

interface LoadAccountPort {

    fun loadAccount(accountId: AccountId, baselineDate: LocalDateTime): Account?

    class AccountNotFoundException(accountId: Account.AccountId) : RuntimeException(
            "No account was found for account ID '$accountId'"
    )
}

