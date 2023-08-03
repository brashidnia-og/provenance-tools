package com.brashidnia.provenance.tools.service.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "service")
data class AppProperties(
    val name: String,
    val environment: String,
    val port: String
) {
    override fun toString(): String {
        return "Service Properties: \nname: $name \nenvironment: $environment \nport: $port"
    }
}