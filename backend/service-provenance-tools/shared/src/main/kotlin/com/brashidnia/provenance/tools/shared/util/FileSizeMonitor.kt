package com.brashidnia.provenance.tools.shared.util

import com.brashidnia.provenance.tools.shared.extension.toPrettyJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mu.KotlinLogging
import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.time.Instant
import kotlin.io.path.Path

class FileSizeMonitor(val path: String, val finalSize: Long? = null) {
    private var last: MonitorStats? = null

    private val log = KotlinLogging.logger { }

    data class MonitorStats(
        val finalSize: Long? = null,
        val lastTime: Instant? = null,
        val currentTime: Instant = Instant.now(),
        val last: Long? = null,
        val current: Long? = null
    ) {
        val diffBytes = if (current == null) null else current - (last ?: 0L)
        val rateKbs = if (diffBytes == null || lastTime == null)
            null
        else (diffBytes / (1024 * (currentTime.toEpochMilli() - lastTime.toEpochMilli()) / 1000L)).let { "${it}Kb/s"}
        val remainingBytes = if (finalSize == null || current == null) null else finalSize - current
    }

    suspend fun monitor(block: MonitorStats?.() -> Unit): Job {
        val initialSize = getFileSize()
        last = MonitorStats(finalSize = finalSize, last = null, current = initialSize)

        return CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                delay(5000L)
                refreshFile()
                block(last)
            }
        }
    }

    suspend fun end(job: Job, reason: String = "Ended Monitor") {
        job.cancel(reason)
    }

    private suspend fun refreshFile() {
        val currentSize = getFileSize()
        val new = MonitorStats(
            finalSize = finalSize,
            lastTime = last?.currentTime,
            currentTime = Instant.now(),
            last = last?.current,
            current = currentSize
        )
        log.info(new.toPrettyJson())
        last = new
    }

    private suspend fun getFileSize(): Long =
        try {
            Files.size(Path(path))
        } catch (e: NoSuchFileException) {
            0
        }
}