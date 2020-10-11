package de.shinythings.hexagon.application.usecase

import de.shinythings.hexagon.application.port.input.SendMoney.Request
import de.shinythings.hexagon.application.port.input.SendMoney.Response
import de.shinythings.hexagon.domain.Account.AccountId
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class SendMoneyTest : DescribeSpec({

    describe("SendMoney") {
        it("sends money") {
            val sendMoney = SendMoney()
            sendMoney(Request(
                    accountId = AccountId(42)
            )) shouldBe Response(success = true)
        }
    }

})
