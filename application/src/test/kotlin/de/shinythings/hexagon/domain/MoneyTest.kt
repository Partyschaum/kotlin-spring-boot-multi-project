package de.shinythings.hexagon.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class MoneyTest : DescribeSpec({

    describe("isPositiveOrZero") {
        Money.of(-1).isPositiveOrZero() shouldBe false
        Money.of(0).isPositiveOrZero() shouldBe true
        Money.of(1).isPositiveOrZero() shouldBe true
    }

    describe("isNegative") {
        Money.of(-1).isNegative() shouldBe true
        Money.of(0).isNegative() shouldBe false
    }

    describe("isPositive") {
        Money.of(0).isPositive() shouldBe false
        Money.of(1).isPositive() shouldBe true
    }

    describe("isGreaterThanOrEqualTo") {
        (Money.of(1) >= Money.of(0)) shouldBe true
        (Money.of(1) >= Money.of(1)) shouldBe true
        (Money.of(1) >= Money.of(2)) shouldBe false
    }

    describe("isGreaterThan") {
        (Money.of(1) > Money.of(0)) shouldBe true
        (Money.of(0) > Money.of(1)) shouldBe false
    }

    describe("plus") {
        (Money.of(1) + Money.of(2)) shouldBe Money.of(3)
    }

    describe("minus") {
        (Money.of(2) - Money.of(1)) shouldBe Money.of(1)
    }

    describe("negate") {
        Money.of(1).negate() shouldBe Money.of(-1)
    }

    describe("add") {
        Money.add(Money.of(1), Money.of(1)) shouldBe Money.of(2)
    }

    describe("subtract") {
        Money.subtract(Money.of(2), Money.of(1)) shouldBe Money.of(1)
    }
})
