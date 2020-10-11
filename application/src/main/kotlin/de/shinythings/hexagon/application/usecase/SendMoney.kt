package de.shinythings.hexagon.application.usecase

import de.shinythings.hexagon.application.UseCase
import de.shinythings.hexagon.application.port.input.SendMoney

class SendMoney : UseCase<SendMoney.Request, SendMoney.Response>() {
    override fun handle(request: SendMoney.Request): SendMoney.Response {
        return SendMoney.Response(success = true)
    }
}
