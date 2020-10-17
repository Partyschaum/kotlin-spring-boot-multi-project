package de.shinythings.hexagon.application.usecase

import de.shinythings.hexagon.application.port.input.GetAccountBalancePort
import de.shinythings.hexagon.application.port.output.LoadAccountPort
import de.shinythings.hexagon.application.port.output.LoadAccountPort.AccountNotFoundException
import java.time.LocalDateTime

class GetAccountBalanceUseCase(
        private val loadAccountPort: LoadAccountPort
) : GetAccountBalancePort() {

    override fun handle(query: GetAccountBalanceQuery): GetAccountBalanceResponse {
        val account = loadAccountPort.loadAccount(
                accountId = query.accountId,
                baselineDate = LocalDateTime.now()
        ) ?: throw AccountNotFoundException(query.accountId)

        return GetAccountBalanceResponse(
                money = account.calculateBalance()
        )
    }
}
