package de.shinythings.hexagon.application.port

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import javax.validation.ConstraintViolationException
import javax.validation.constraints.Size

class CommandQueryValidationTest : DescribeSpec() {

    init {
        describe("Validation of Command and Query input ports") {
            context("Command") {
                it("validates the input passed to the command implementation") {
                    val command = CommandDummy()

                    command(CommandDummy.Command("short string"))

                    command.handleWasCalled shouldBe true
                }

                it("throws when the input does not adhere to the requirements") {
                    val command = CommandDummy()

                    shouldThrow<ConstraintViolationException> {
                        command(CommandDummy.Command("this string is longer than 15 characters"))
                    }
                }
            }

            context("Query") {
                it("validates the input passed to the query implementation") {
                    val query = QueryDummy()

                    query(QueryDummy.Query("short string"))

                    query.handleWasCalled shouldBe true
                }

                it("throws when the input does not adhere to the requirements") {
                    val query = QueryDummy()

                    shouldThrow<ConstraintViolationException> {
                        query(QueryDummy.Query("this string is longer than 15 characters"))
                    }
                }
            }

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
}


