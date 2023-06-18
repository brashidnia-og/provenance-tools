package com.brashidnia.provenance.tools.service.frameworks.log

import com.brashidnia.provenance.tools.service.frameworks.command.CommandExecutorService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.io.File
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

const val ONE_HOUR_IN_MILLISECONDS = 1000L * 60 * 60

@Service
class LogRotatorJob(
    private val commandExecutorService: CommandExecutorService,
    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ssz"),
    private val deleteDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
) {
    private val networks: List<String> = listOf("pio-testnet-1", "pio-mainnet-1")
    private val logFileDirFormat: String = "/var/provenance/%s/log"
    private val daysToKeep: Long = 1

    private fun getLogFileDir(networkName: String): String = logFileDirFormat.format(networkName)

    companion object {
        val LOG = LoggerFactory.getLogger(LogRotatorJob::class.java.name)
    }

    // Once an hour at (XX:00:00)
//    @Scheduled(cron = "0 0 * * * *")
    @Scheduled(cron = "0 * * * * *")
    fun rotate() {
        for (network in networks) {
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

    // Once a day (at 00:00:00)
//    @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(cron = "0 * * * * *")
    fun delete() {
        for (network in networks) {
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