package de.shinythings.hexagon.adapter.web

import com.ninjasquad.springmockk.MockkBean
import de.shinythings.hexagon.application.port.input.SendMoneyPort
import de.shinythings.hexagon.application.port.input.SendMoneyPort.SendMoneyCommand
import de.shinythings.hexagon.domain.Account.AccountId
import de.shinythings.hexagon.domain.Money
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class SendMoneyControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var sendMoneyPort: SendMoneyPort

    @Test
    fun `sends money to the account`() {
        every { sendMoneyPort.invoke(any()) } returns Unit

        mockMvc.perform(post("/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}", 41, 42, 500)
                .header("Content-Type", "application/json"))
                .andExpect(status().isOk)

        verify {
            sendMoneyPort.invoke(SendMoneyCommand(
                    sourceAccountId = AccountId(41),
                    targetAccountId = AccountId(42),
                    money = Money.of(500L)
            ))
        }
    }
}
