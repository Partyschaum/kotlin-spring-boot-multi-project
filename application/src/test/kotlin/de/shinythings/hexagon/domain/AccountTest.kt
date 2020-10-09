package de.shinythings.hexagon.domain

import de.shinythings.hexagon.domain.Account.AccountId
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class AccountTest : DescribeSpec({

    describe("calculateBalance") {
        it("calculates the balance from the baseline balance and the ActivityWindow") {
            val account = Account(
                    id = AccountId(1),
                    baselineBalance = Money.of(10),
                    activityWindow = ActivityWindow(
                            mutableListOf(
                                    Activity(
                                            id = Activity.ActivityId(1),
                                            ownerAccountId = AccountId(1),
                                            sourceAccountId = AccountId(2),
                                            targetAccountId = AccountId(1),
                                            timestamp = LocalDateTime.parse("2020-10-09T20:52:58.873330"),
                                            money = Money.of(5)
                                    ),
                            )
                    )
            )

            account.calculateBalance() shouldBe Money.of(15)
        }
    }

    describe("withdraw") {
        it("may withdraw money") {
            val account = Account(
                    id = AccountId(1),
                    baselineBalance = Money.of(10),
                    activityWindow = ActivityWindow(
                            mutableListOf(
                                    Activity(
                                            id = Activity.ActivityId(1),
                                            ownerAccountId = AccountId(1),
                                            sourceAccountId = AccountId(2),
                                            targetAccountId = AccountId(1),
                                            timestamp = LocalDateTime.parse("2020-10-09T20:52:58.873330"),
                                            money = Money.of(5)
                                    ),
                            )
                    )
            )

            account.withdraw(
                    money = Money.of(10),
                    targetAccountId = AccountId(1)
            ) shouldBe true
        }

        it("may not withdraw money") {
            val account = Account(
                    id = AccountId(1),
                    baselineBalance = Money.of(10),
                    activityWindow = ActivityWindow(
                            mutableListOf(
                                    Activity(
                                            id = Activity.ActivityId(1),
                                            ownerAccountId = AccountId(1),
                                            sourceAccountId = AccountId(2),
                                            targetAccountId = AccountId(1),
                                            timestamp = LocalDateTime.parse("2020-10-09T20:52:58.873330"),
                                            money = Money.of(5)
                                    ),
                            )
                    )
            )

            account.withdraw(
                    money = Money.of(16),
                    targetAccountId = AccountId(1)
            ) shouldBe false
        }
    }

    describe("deposit") {
        it("adds money to the account") {
            val account = Account(
                    id = AccountId(1),
                    baselineBalance = Money.of(10),
                    activityWindow = ActivityWindow(mutableListOf())
            )

            account.deposit(
                    money = Money.of(20),
                    sourceAccountId = AccountId(2)
            )

            account.calculateBalance() shouldBe Money.of(30)
        }
    }
})
