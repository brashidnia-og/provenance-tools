package com.brashidnia.provenance.tools.service.config

import com.brashidnia.provenance.tools.service.config.properties.AppProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableConfigurationProperties(
    value = [
        AppProperties::class
    ]
)
@EnableScheduling
class AppConfig {

    @Primary
    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper().registerModule(JavaTimeModule())
}