package com.brashidnia.provenance.tools.script

import com.brashidnia.provenance.tools.shared.client.ChainId
import com.brashidnia.provenance.tools.shared.client.QuickSyncClient
import com.brashidnia.provenance.tools.shared.extension.toPrettyJson
import com.google.cloud.storage.StorageOptions
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

val ktorHttpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
}
val gcClient = StorageOptions.getDefaultInstance().getService()
val quickSyncClient = QuickSyncClient(gcClient, ChainId.TESTNET)

fun main(vararg args: String) {
    val backups = runBlocking { quickSyncClient.getBackups() }
    val latestIndexed = backups
        .filter { it.name.contains("indexed") }
        .sortedBy { it.metadata?.get("provenance-version") ?: "v0.0.0" }
        .last()
    println("Latest Indexed: ${latestIndexed.toPrettyJson()}")

    runBlocking {
        quickSyncClient.downloadWithSpeed(latestIndexed, "/home/bobak/Downloads/${latestIndexed.name}")
    }
}