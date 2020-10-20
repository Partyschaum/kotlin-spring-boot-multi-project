package de.shinythings.hexagon.application.port

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.validation.ConstraintViolationException
import javax.validation.constraints.Size

class CommandQueryValidationTest {

    @Test
    fun `the command port validates the input passed to the command implementation`() {
        val command = CommandDummy()

        command(CommandDummy.Command("short string"))

        assertThat(command.handleWasCalled).isTrue()
    }

    @Test
    fun `the command port validation throws when the input does not adhere to the validations`() {
        val command = CommandDummy()

        assertThrows<ConstraintViolationException> {
            command(CommandDummy.Command("this string is longer than 15 characters"))
        }
    }

    @Test
    fun `the query port validates the input passed to the query implementation`() {
        val query = QueryDummy()

        query(QueryDummy.Query("short string"))

        assertThat(query.handleWasCalled).isTrue()
    }

    @Test
    fun `the query port validation throws when the input does not adhere to the validations`() {
        val query = QueryDummy()

        assertThrows<ConstraintViolationException> {
            query(QueryDummy.Query("this string is longer than 15 characters"))
        }
    }

    private class CommandDummy : Command<CommandDummy.Command>() {

        var handleWasCalled = false

        data class Command(
                @field:Size(min = 3, max = 15)
                val someFieldToBeValidated: String
        )

        override fun handle(command: Command) {
            handleWasCalled = true
        }
    }

    private class QueryDummy : Query<QueryDummy.Query, Unit>() {

        var handleWasCalled = false

        data class Query(
                @field:Size(min = 3, max = 15)
                val someFieldToBeValidated: String
        )

        override fun handle(query: Query) {
            handleWasCalled = true
        }
    }
}
