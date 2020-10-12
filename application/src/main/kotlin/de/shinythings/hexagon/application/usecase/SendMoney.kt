package de.shinythings.hexagon.application.usecase

import de.shinythings.hexagon.application.port.input.SendMoney
import de.shinythings.hexagon.application.port.out.LoadAccount
import de.shinythings.hexagon.application.port.out.UpdateAccount
import java.time.LocalDateTime

class SendMoney(
        private val loadAccount: LoadAccount,
        private val updateAccount: UpdateAccount
) : SendMoney() {

    override fun handle(command: SendMoneyCommand) {
        val account = loadAccount.loadAccount(
                accountId = command.sourceAccountId,
                baselineDate = LocalDateTime.now()
        )

        account.deposit(
                money = command.money,
                sourceAccountId = command.sourceAccountId
        )

        updateAccount.updateActivities(account)
    }
}
