package com.brashidnia.provenance.tools.shared.client

import com.brashidnia.provenance.tools.shared.extension.toPrettyJson
import com.brashidnia.provenance.tools.shared.util.FileSizeMonitor
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import kotlin.io.path.Path

enum class ChainId(val bucket: String) {
    TESTNET("provenance-testnet-backups"),
    MAINNET("provenance-mainnet-backups")
}

class QuickSyncClient(
    private val gcClient: Storage,
    private val bucket: String
) {
    private val log = KotlinLogging.logger { }

    constructor(gcClient: Storage, chainId: ChainId) : this(
        gcClient,
        chainId.bucket
    )

    suspend fun getBackups(): List<BlobInfo> {
        val backups = gcClient.list(bucket)
        println(backups.streamAll().toList().map { it.asBlobInfo() }.toPrettyJson())
        return backups.streamAll().toList().map { it.asBlobInfo() }
    }

    suspend fun download(blobName: String, generation: Long, downloadPath: String) {
        gcClient.downloadTo(BlobId.of(bucket, blobName, generation), Path(downloadPath))
    }

    suspend fun download(blobInfo: BlobInfo, downloadPath: String) {
        gcClient.downloadTo(blobInfo.blobId, Path(downloadPath))
    }

    suspend fun downloadWithSpeed(blobInfo: BlobInfo, downloadPath: String) {
        val monitor = FileSizeMonitor(path = downloadPath, finalSize = blobInfo.size).monitor {}

        runBlocking { gcClient.downloadTo(blobInfo.blobId, Path(downloadPath)) }

        monitor.cancel("Download complete")
    }
}