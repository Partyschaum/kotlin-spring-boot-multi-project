package de.shinythings.hexagon.application.usecase

import de.shinythings.hexagon.application.port.input.GetAccountBalance
import de.shinythings.hexagon.application.port.out.LoadAccount
import de.shinythings.hexagon.application.port.out.LoadAccount.AccountNotFoundException
import java.time.LocalDateTime

class GetAccountBalance(
        private val loadAccount: LoadAccount
) : GetAccountBalance() {

    override fun handle(query: GetAccountBalanceQuery): GetAccountBalanceResponse {
        val account = loadAccount.loadAccount(
                accountId = query.accountId,
                baselineDate = LocalDateTime.now()
        ) ?: throw AccountNotFoundException(query.accountId)

        return GetAccountBalanceResponse(
                money = account.calculateBalance()
        )
    }
}
