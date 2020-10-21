package de.shinythings.hexagon

import de.shinythings.hexagon.application.MoneyTransferProperties
import de.shinythings.hexagon.domain.Money
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(HexagonConfigurationProperties::class)
class HexagonConfiguration {

    /**
     * Adds a use-case-specific {@link MoneyTransferProperties} object to the application context. The properties
     * are read from the Spring-Boot-specific {@link BuckPalConfigurationProperties} object.
     */
    @Bean
    fun moneyTransferProperties(hexagonConfigurationProperties: HexagonConfigurationProperties) =
            MoneyTransferProperties(
                    maximumTransferThreshold = Money.of(hexagonConfigurationProperties.transferThreshold)
            )
}
