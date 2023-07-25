package com.brashidnia.provenance.tools.service.config

import com.brashidnia.provenance.tools.service.config.properties.AppProperties
import com.brashidnia.provenance.tools.service.config.properties.PbNodeProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableConfigurationProperties(
    value = [
        AppProperties::class,
        PbNodeProperties::class
    ]
)
@EnableScheduling
class AppConfig(private val pbNodeProperties: PbNodeProperties) {

    @Primary
    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper().registerModule(JavaTimeModule())

    @Bean(name = ["networkConfigs"])
    fun networkConfigs(): Map<String, String> = mapOf(
        "pio-testnet-1" to pbNodeProperties.testnetDir,
        "pio-mainnet-1" to pbNodeProperties.mainnetDir
    )
}
