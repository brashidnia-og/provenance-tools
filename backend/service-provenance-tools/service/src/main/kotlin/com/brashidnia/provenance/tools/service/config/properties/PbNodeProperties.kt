package com.brashidnia.provenance.tools.service.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "pb.node")
data class PbNodeProperties(
    val testnetDirs: String,
    val mainnetDirs: String,
) {
    val testnetDirList: List<String> = testnetDirs.split(",")
    val mainnetDirList: List<String> = mainnetDirs.split(",")
}
