package com.brashidnia.provenance.tools.service.config

import com.brashidnia.provenance.tools.service.config.properties.AppProperties
import com.brashidnia.provenance.tools.service.config.properties.PbNodeProperties
import com.brashidnia.provenance.tools.shared.client.ChainId
import com.brashidnia.provenance.tools.shared.client.QuickSyncClient
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
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
    fun networkConfigs(): Map<String, List<String>> = mapOf(
        "pio-testnet-1" to pbNodeProperties.testnetDirList,
        "pio-mainnet-1" to pbNodeProperties.mainnetDirList
    )

    @Bean
    open fun ktorHttpClient(): HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    @Bean
    fun storage() = StorageOptions.getDefaultInstance().getService()

    @Bean
    fun quickSyncClients(
        gc: Storage
    ): Map<ChainId, QuickSyncClient> = ChainId.values().associateWith { QuickSyncClient(gc, it) }
}
