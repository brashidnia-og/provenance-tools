package com.brashidnia.provenance.tools.service.frameworks.log

import com.brashidnia.provenance.tools.service.frameworks.command.CommandExecutorService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.io.File
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class LogRotatorJob(
    private val commandExecutorService: CommandExecutorService,
    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ssz"),
    private val deleteDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"),
    @Qualifier("networkConfigs") private val  networkConfigs: Map<String, String>
) {
    private val logFileDirFormat: String = "%s/log"
    private val daysToKeep: Long = 2

    private fun getLogFileDir(networkName: String): String = networkConfigs[networkName]?.let {
        logFileDirFormat.format(it)
    } ?: error("Unsupported network: $networkName")

    companion object {
        val LOG = LoggerFactory.getLogger(LogRotatorJob::class.java.name)
    }

    // Once an hour (at yyyy-MM-dd XX:00:00)
    @Scheduled(cron = "0 0 * * * *")
    fun rotate() {
        for (network in networkConfigs.keys.filter { networkConfigs[it]!!.isNotBlank() }) {
            try {
                val dateTime = LocalDateTime.now().atZone(ZoneId.systemDefault())
                val formattedDateTime = dateTime.format(dateTimeFormatter)
                val logFilePath = getLogFileDir(network)

                LOG.info("Backing up log: $logFilePath/node.log to $logFilePath/node_$formattedDateTime.log")
                commandExecutorService.execute(listOf("cp $logFilePath/node.log $logFilePath/node_$formattedDateTime.log"))

                LOG.info("Truncating active log: $logFilePath/node.log")
                commandExecutorService.execute(listOf(": > $logFilePath/node.log"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Once a day (at yyyy-MM-dd 00:00:00)
    @Scheduled(cron = "0 0 0 * * *")
    fun delete() {
        for (network in networkConfigs.keys.filter { networkConfigs[it]!!.isNotBlank() }) {
            try {
                val dateTime = LocalDateTime.now().atZone(ZoneId.systemDefault())
                val oldestDateTimeToKeep = dateTime.minusDays(daysToKeep)

                val formattedOldestDateTime = oldestDateTimeToKeep.format(deleteDateTimeFormatter)
                val logFilePath = getLogFileDir(network)

                LOG.info("Deleting logs in $logFilePath older than: $formattedOldestDateTime")
                val filesToDelete = getFilesToDelete(logFilePath, formattedOldestDateTime)
                LOG.info(filesToDelete.toString())

                for (file in filesToDelete) {
                    commandExecutorService.execute(listOf("rm $logFilePath/$file"))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getFilesToDelete(dir: String, oldestDateTimeToKeep: String): List<String> {
        val filesInDir = checkNotNull(File(dir).listFiles()).map { it.name }
        return filesInDir.filter { it != "node.log" && it < "node_$oldestDateTimeToKeep" }
    }
}