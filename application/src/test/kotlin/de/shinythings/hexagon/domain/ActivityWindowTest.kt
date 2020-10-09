package de.shinythings.hexagon.domain

import de.shinythings.hexagon.domain.Account.AccountId
import de.shinythings.hexagon.domain.Activity.ActivityId
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDateTime

class ActivityWindowTest : DescribeSpec({

    describe("startTimestamp") {
        it("returns the oldest timestamp from the ActivityWindow") {
            val activityWindow = ActivityWindow(
                    mutableListOf(
                            Activity(
                                    id = ActivityId(1),
                                    ownerAccountId = AccountId(1),
                                    sourceAccountId = AccountId(1),
                                    targetAccountId = AccountId(1),
                                    timestamp = LocalDateTime.parse("2020-10-08T20:52:58.873330"),
                                    money = Money.of(1)
                            ),
                            Activity(
                                    id = ActivityId(2),
                                    ownerAccountId = AccountId(1),
                                    sourceAccountId = AccountId(1),
                                    targetAccountId = AccountId(1),
                                    timestamp = LocalDateTime.parse("2020-10-09T20:52:58.873330"),
                                    money = Money.of(1)
                            )
                    )
            )

            activityWindow.startTimestamp() shouldBe LocalDateTime.parse("2020-10-08T20:52:58.873330")
            activityWindow.startTimestamp() shouldNotBe LocalDateTime.parse("2020-10-09T20:52:58.873330")
        }

        it("throws an IllegalStateException when the ActivityWindows contains no activities") {
            shouldThrow<IllegalStateException> {
                ActivityWindow(mutableListOf()).startTimestamp()
            }.message shouldBe "ActivityWindow has not activities yet!"
        }

    }

    describe("endTimestamp") {
        it("returns the latest timestamp from the ActivityWindow") {
            val activityWindow = ActivityWindow(
                    mutableListOf(
                            Activity(
                                    id = ActivityId(1),
                                    ownerAccountId = AccountId(1),
                                    sourceAccountId = AccountId(1),
                                    targetAccountId = AccountId(1),
                                    timestamp = LocalDateTime.parse("2020-10-08T20:52:58.873330"),
                                    money = Money.of(1)
                            ),
                            Activity(
                                    id = ActivityId(2),
                                    ownerAccountId = AccountId(1),
                                    sourceAccountId = AccountId(1),
                                    targetAccountId = AccountId(1),
                                    timestamp = LocalDateTime.parse("2020-10-09T20:52:58.873330"),
                                    money = Money.of(1)
                            )
                    )
            )

            activityWindow.endTimestamp() shouldBe LocalDateTime.parse("2020-10-09T20:52:58.873330")
            activityWindow.endTimestamp() shouldNotBe LocalDateTime.parse("2020-10-08T20:52:58.873330")
        }

        it("throws an IllegalStateException when the ActivityWindow contains no activities") {
            shouldThrow<IllegalStateException> {
                ActivityWindow(mutableListOf()).endTimestamp()
            }.message shouldBe "ActivityWindow has not activities yet!"
        }
    }

    describe("calculateBalance") {
        it("returns the balance calculated from the deposits and the withdrawals") {
            val activityWindow = ActivityWindow(
                    mutableListOf(
                            Activity(
                                    id = ActivityId(1),
                                    ownerAccountId = AccountId(1),
                                    sourceAccountId = AccountId(2),
                                    targetAccountId = AccountId(1),
                                    timestamp = LocalDateTime.parse("2020-10-09T20:52:58.873330"),
                                    money = Money.of(5)
                            ),
                            Activity(
                                    id = ActivityId(2),
                                    ownerAccountId = AccountId(1),
                                    sourceAccountId = AccountId(2),
                                    targetAccountId = AccountId(1),
                                    timestamp = LocalDateTime.parse("2020-10-10T20:52:58.873330"),
                                    money = Money.of(5)
                            ),
                            Activity(
                                    id = ActivityId(2),
                                    ownerAccountId = AccountId(1),
                                    sourceAccountId = AccountId(1),
                                    targetAccountId = AccountId(2),
                                    timestamp = LocalDateTime.parse("2020-10-11T20:52:58.873330"),
                                    money = Money.of(7)
                            ),
                    )
            )

            activityWindow.calculateBalance(AccountId(1)) shouldBe Money.of(3)
        }
    }
})
