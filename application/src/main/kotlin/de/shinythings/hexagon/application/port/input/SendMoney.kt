package de.shinythings.hexagon.application.port.input

import de.shinythings.hexagon.application.port.Command
import de.shinythings.hexagon.application.port.input.SendMoney.SendMoneyCommand
import de.shinythings.hexagon.domain.Account.AccountId
import de.shinythings.hexagon.domain.Money

abstract class SendMoney : Command<SendMoneyCommand>() {

    data class SendMoneyCommand(
            val sourceAccountId: AccountId,
            val targetAccountId: AccountId,
            val money: Money
    )
}
