package de.shinythings.hexagon.application.usecase

import de.shinythings.hexagon.application.port.input.GetAccountBalance
import de.shinythings.hexagon.application.port.out.LoadAccount
import java.time.LocalDateTime

class GetAccountBalance(
        private val loadAccount: LoadAccount
) : GetAccountBalance() {

    override fun handle(query: GetAccountBalanceQuery): GetAccountBalanceResponse {
        val account = loadAccount.loadAccount(
                accountId = query.accountId,
                baselineDate = LocalDateTime.now()
        )

        return GetAccountBalanceResponse(
                money = account.calculateBalance()
        )
    }
}
