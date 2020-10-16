package de.shinythings.hexagon.application.port.input

import de.shinythings.hexagon.application.port.Command
import de.shinythings.hexagon.application.port.input.SendMoneyPort.SendMoneyCommand
import de.shinythings.hexagon.domain.Account.AccountId
import de.shinythings.hexagon.domain.Money

abstract class SendMoneyPort : Command<SendMoneyCommand>() {

    data class SendMoneyCommand(
            val sourceAccountId: AccountId,
            val targetAccountId: AccountId,
            val money: Money
    )
}
