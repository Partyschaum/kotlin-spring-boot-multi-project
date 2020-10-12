package de.shinythings.hexagon.application.port.input

import de.shinythings.hexagon.application.port.Query
import de.shinythings.hexagon.application.port.input.GetAccountBalance.GetAccountBalanceQuery
import de.shinythings.hexagon.application.port.input.GetAccountBalance.GetAccountBalanceResponse
import de.shinythings.hexagon.domain.Account.AccountId
import de.shinythings.hexagon.domain.Money

abstract class GetAccountBalance : Query<GetAccountBalanceQuery, GetAccountBalanceResponse>() {

    data class GetAccountBalanceQuery(val accountId: AccountId)

    data class GetAccountBalanceResponse(val money: Money)
}
