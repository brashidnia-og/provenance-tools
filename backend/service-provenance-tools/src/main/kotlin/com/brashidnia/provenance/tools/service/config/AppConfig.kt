package com.brashidnia.provenance.tools.service.config

import com.brashidnia.provenance.tools.service.config.properties.AppProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(
    value = [
        AppProperties::class
    ]
)
class AppConfig {
}