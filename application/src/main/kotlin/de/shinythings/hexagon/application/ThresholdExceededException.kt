package de.shinythings.hexagon.application

import de.shinythings.hexagon.domain.Money

class ThresholdExceededException(threshold: Money, actual: Money) : RuntimeException(
        "Maximum threshold for transferring money exceeded: tried to transfer $actual but threshold is $threshold!"
)
