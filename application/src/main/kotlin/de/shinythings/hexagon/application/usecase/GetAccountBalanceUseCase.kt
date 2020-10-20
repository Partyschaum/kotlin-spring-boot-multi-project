package de.shinythings.hexagon.application.usecase

import de.shinythings.hexagon.application.port.input.GetAccountBalancePort
import de.shinythings.hexagon.application.port.output.LoadAccountPort
import de.shinythings.hexagon.application.port.output.LoadAccountPort.AccountNotFoundException
import java.time.LocalDateTime

class GetAccountBalanceUseCase(
        private val loadAccountPort: LoadAccountPort
) : GetAccountBalancePort() {

    override fun handle(query: GetAccountBalanceQuery): GetAccountBalanceResponse {

        val account = loadAccountPort.loadAccountOrNull(
                accountId = query.accountId,
                baselineDate = LocalDateTime.now()
        ) ?: throw AccountNotFoundException(query.accountId)

        val money = account.calculateBalance()
        return GetAccountBalanceResponse(
                money = money
        )
    }
}
