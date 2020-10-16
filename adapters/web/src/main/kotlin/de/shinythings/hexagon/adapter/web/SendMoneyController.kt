package de.shinythings.hexagon.adapter.web

import de.shinythings.hexagon.application.port.input.SendMoney
import de.shinythings.hexagon.common.WebAdapter
import de.shinythings.hexagon.domain.Account.AccountId
import de.shinythings.hexagon.domain.Money
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@WebAdapter
@RestController
class SendMoneyController(
        private val sendMoney: SendMoney
) {

    @PostMapping(path = ["/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}"])
    fun sendMoney(
            @PathVariable("sourceAccountId")
            sourceAccountId: Long,
            @PathVariable("targetAccountId")
            targetAccountId: Long,
            @PathVariable("amount")
            amount: Long
    ) {
        val command = SendMoney.SendMoneyCommand(
                sourceAccountId = AccountId(sourceAccountId),
                targetAccountId = AccountId(targetAccountId),
                money = Money.of(amount)
        )

        sendMoney(command)
    }
}
