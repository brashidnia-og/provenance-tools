package com.brashidnia.provenance.tools.service.config

import com.brashidnia.provenance.tools.service.config.properties.AppProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableConfigurationProperties(
    value = [
        AppProperties::class
    ]
)
@EnableScheduling
class AppConfig {
}