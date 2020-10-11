package de.shinythings.hexagon.application.port.input

import de.shinythings.hexagon.application.port.Input
import de.shinythings.hexagon.application.port.input.SendMoney.Request
import de.shinythings.hexagon.domain.Account.AccountId

interface SendMoney : Input<Request, Unit> {
    data class Request(val accountId: AccountId)
    data class Response(val success: Boolean)
}
