package com.brashidnia.provenance.tools.service.frameworks.web.api.v1

import com.brashidnia.provenance.tools.service.domain.api.LogFormat
import com.brashidnia.provenance.tools.service.domain.api.LogResponse
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.io.input.ReversedLinesFileReader
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.io.File
import java.nio.charset.Charset
import java.security.Principal

@RestController
@RequestMapping("/api/v1/logs")
@PreAuthorize("hasRole('USER')")
class LogController(private val objectMapper: ObjectMapper) {
    companion object {
        val LOG = LoggerFactory.getLogger(LogController::class.java.name)
    }


    @GetMapping("/latest/{chainId}")
    fun getLatestLogs(
        principal: Principal,
        @PathVariable chainId: String? = "pio-testnet-1",
        @RequestParam format: LogFormat? = LogFormat.JSON,
        @RequestParam lines: Int?
    ): Mono<LogResponse> =
        Mono.defer {
            LOG.info(chainId)

            val totalLinesToProcess = lines ?: 100
            val fileDir = "/var/provenance/$chainId/log/"
            val currentFileName = "node.log"
            val filesInDir = checkNotNull(File(fileDir).listFiles()).map { it.name }.sortedDescending()
            var processableFiles: List<String> = filesInDir.filter { it != currentFileName }

            var totalLinesProcessed = 0;

            // Read from the active node.log file first
            val activeFilePath = "$fileDir$currentFileName"
            val logLines: MutableList<String> = readLines(
                quantityOfLinesToRead = lines,
                filePath = activeFilePath
            ).toMutableList()
            totalLinesProcessed += logLines.size


            while (totalLinesProcessed <= totalLinesToProcess) {
                // Get next log file (time order). If not exist, return current result
                val maxFileName = getMaxFile(processableFiles) ?: break

                logLines.addAll(
                    readLines(
                        quantityOfLinesToRead = totalLinesToProcess - totalLinesProcessed,
                        filePath = "$fileDir$maxFileName"
                    )
                )

                // Mark log file as processed
                processableFiles = processableFiles.subList(fromIndex = 1, toIndex = processableFiles.size)
            }

            val logs = when (format) {
                LogFormat.STRING -> logLines
                LogFormat.JSON -> parseToJson(rawLines = logLines)
            }
            Mono.just(LogResponse("SUCCESS", logs))
        }

    fun parseToJson(rawLines: List<String>): List<JsonNode> =
        rawLines.map { rawLine -> objectMapper.readValue(rawLine, JsonNode::class.java) }

    fun getMaxFile(filesNamesSortedDescending: List<String>): String? = filesNamesSortedDescending.firstOrNull()

    fun readLines(quantityOfLinesToRead: Int?, filePath: String): List<String> {
        val file = File(filePath)
        var counter = 0
        val rawLog: MutableList<String> = ArrayList<String>()

        // Read from end to beginning (newest to oldest)
        val reader = ReversedLinesFileReader(file, Charset.defaultCharset())
        val numberOfLines = quantityOfLinesToRead ?: 100

        // Read lines until limit is reached, or no lines left to read
        while (counter < numberOfLines) {
            val line = reader.readLine()
            // Break if the EOF has been reached
            if (line.isNullOrBlank()) break

            // Add older line to the beginning
            rawLog.add(0, line)
            counter++
        }

        return rawLog
    }
}