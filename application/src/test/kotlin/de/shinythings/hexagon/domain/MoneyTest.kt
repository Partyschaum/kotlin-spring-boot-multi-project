package de.shinythings.hexagon.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MoneyTest {

    @Test
    fun `is positive or zero`() {
        assertThat(Money.of(-1).isPositiveOrZero()).isFalse()
        assertThat(Money.of(0).isPositiveOrZero()).isTrue()
        assertThat(Money.of(1).isPositiveOrZero()).isTrue()
    }

    @Test
    fun `is negative`() {
        assertThat(Money.of(-1).isNegative()).isTrue()
        assertThat(Money.of(0).isNegative()).isFalse()
    }

    @Test
    fun `is positive`() {
        assertThat(Money.of(0).isPositive()).isFalse()
        assertThat(Money.of(1).isPositive()).isTrue()
    }

    @Test
    fun `is greater than or equal to`() {
        assertThat((Money.of(1) >= Money.of(0))).isTrue()
        assertThat((Money.of(1) >= Money.of(1))).isTrue()
        assertThat((Money.of(1) >= Money.of(2))).isFalse()
    }

    @Test
    fun `is greater than`() {
        assertThat((Money.of(1) > Money.of(0))).isTrue()
        assertThat((Money.of(0) > Money.of(1))).isFalse()
    }

    @Test
    fun plus() {
        assertThat((Money.of(1) + Money.of(2))).isEqualTo(Money.of(3))
    }

    @Test
    fun minus() {
        assertThat((Money.of(2) - Money.of(1))).isEqualTo(Money.of(1))
    }

    @Test
    fun negate() {
        assertThat(Money.of(1).negate()).isEqualTo(Money.of(-1))
    }

    @Test
    fun add() {
        assertThat(Money.add(Money.of(1), Money.of(1))).isEqualTo(Money.of(2))
    }

    @Test
    fun subtract() {
        assertThat(Money.subtract(Money.of(2), Money.of(1))).isEqualTo(Money.of(1))
    }
}
