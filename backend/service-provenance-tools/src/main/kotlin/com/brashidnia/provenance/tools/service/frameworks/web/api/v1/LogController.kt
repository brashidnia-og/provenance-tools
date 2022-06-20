package com.brashidnia.provenance.tools.service.frameworks.web.api.v1

import com.brashidnia.provenance.tools.service.domain.api.LogResponse
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
class LogController {
    companion object {
        val LOG = LoggerFactory.getLogger(LogController::class.java.name)
    }

    @GetMapping("/latest/{chainId}")
    fun getLatestLogs(
        principal: Principal,
        @PathVariable chainId: String,
        @RequestParam lines: Int?
    ): Mono<LogResponse> =
        Mono.defer {
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
            Mono.just(LogResponse("SUCCESS", rawLog))
        }
}