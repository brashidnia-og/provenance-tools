package com.brashidnia.provenance.tools.service.frameworks.web.api.v1

import com.brashidnia.provenance.tools.service.domain.api.LogResponse
import org.apache.commons.io.input.ReversedLinesFileReader
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.io.File
import java.nio.charset.Charset

@RestController
@RequestMapping("/api/v1/logs")
class LogController {
    companion object {
        val LOG = LoggerFactory.getLogger(LogController::class.java.name)
    }

    @GetMapping("/latest/{chainId}")
    fun getLatestLogs(
        @PathVariable chainId: String,
        @RequestParam lines: Int?
    ): LogResponse {
        LOG.info(chainId)
        val filePath = "/var/provenance/$chainId/log/node.log"
        val file = File(filePath)
        var counter = 0
        val rawLog: MutableList<String> = ArrayList<String>()
        val reader = ReversedLinesFileReader(file, Charset.defaultCharset())
        val numberOfLines = lines ?: 100
        while (counter < numberOfLines) {
            val line = reader.readLine() ?: break
            rawLog.add(0, line)
            counter++
        }
        return LogResponse("SUCCESS", rawLog)
    }
}