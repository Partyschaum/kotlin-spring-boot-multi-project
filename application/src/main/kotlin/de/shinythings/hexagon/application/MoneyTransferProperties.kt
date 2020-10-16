package de.shinythings.hexagon.application

import de.shinythings.hexagon.domain.Money

data class MoneyTransferProperties(
        val maximumTransferThreshold: Money = Money.of(1_000_000L)
)
