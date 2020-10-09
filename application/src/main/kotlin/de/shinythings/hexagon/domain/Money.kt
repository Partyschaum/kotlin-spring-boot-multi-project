package de.shinythings.hexagon.domain

import java.math.BigInteger

data class Money(val amount: BigInteger) {

    fun isPositiveOrZero() = amount >= BigInteger.ZERO

    fun isNegative() = amount < BigInteger.ZERO

    fun isPositive() = amount > BigInteger.ZERO

    fun negate() = Money(amount.negate())

    operator fun plus(money: Money) = Money(amount.add(money.amount))

    operator fun minus(money: Money) = Money(amount.subtract(money.amount))

    operator fun compareTo(money: Money): Int {
        return amount.compareTo(money.amount)
    }

    companion object {
        val ZERO = Money.of(0)

        fun of(value: Long) = Money(BigInteger.valueOf(value))

        fun add(a: Money, b: Money) = a.plus(b)

        fun subtract(a: Money, b: Money) = a.minus(b)
    }
}
