package com.brashidnia.provenance.tools.service.frameworks.log

import com.brashidnia.provenance.tools.service.frameworks.command.CommandExecutorService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

const val ONE_HOUR_IN_MILLISECONDS = 1000L * 60 * 60

@Service
class LogRotatorJob(
    private val commandExecutorService: CommandExecutorService,
    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ssz"),
    private val logFilePath: String = "/var/provenance/pio-mainnet-1/log"
) {
    companion object {
        val LOG = LoggerFactory.getLogger(LogRotatorJob::class.java.name)
    }

    @Scheduled(fixedRate = ONE_HOUR_IN_MILLISECONDS)
    fun rotate() {
        try {
            val dateTime = LocalDateTime.now().atZone(ZoneId.systemDefault())
            val formattedDateTime = dateTime.format(dateTimeFormatter)

            LOG.info("Backing up log: ${logFilePath}/node.log to ${logFilePath}/node_$formattedDateTime")
            commandExecutorService.execute(listOf("cp ${logFilePath}/node.log ${logFilePath}/node_$formattedDateTime"))

            LOG.info("Truncating active log: ${logFilePath}/node.log")
            commandExecutorService.execute(listOf(": > ${logFilePath}/node.log"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}