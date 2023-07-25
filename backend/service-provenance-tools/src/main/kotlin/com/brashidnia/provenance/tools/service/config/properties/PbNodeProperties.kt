package com.brashidnia.provenance.tools.service.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated

@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "pb.node")
data class PbNodeProperties(
    val testnetDir: String,
    val mainnetDir: String,
)
