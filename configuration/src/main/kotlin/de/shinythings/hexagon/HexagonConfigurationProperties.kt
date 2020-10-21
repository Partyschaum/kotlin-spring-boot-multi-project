package de.shinythings.hexagon

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "hexagon")
data class HexagonConfigurationProperties(
        val transferThreshold: Long = Long.MAX_VALUE
)
