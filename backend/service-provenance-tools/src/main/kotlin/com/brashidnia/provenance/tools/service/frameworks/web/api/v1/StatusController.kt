package com.brashidnia.provenance.tools.service.frameworks.web.api.v1

import com.brashidnia.provenance.tools.service.domain.api.CmdStatusResponse
import com.brashidnia.provenance.tools.service.domain.api.StatusResponse
import com.brashidnia.provenance.tools.service.domain.api.error.ServerError
import com.brashidnia.provenance.tools.service.frameworks.command.CommandExecutorService
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.io.BufferedReader
import java.io.InputStreamReader

@RestController
@RequestMapping("/api/v1/status")
@PreAuthorize("hasRole('ADMIN')")
class StatusController(
    private val commandExecutorService: CommandExecutorService
) {
    companion object {
        val LOG = LoggerFactory.getLogger(StatusController::class.java.name)
    }

    @GetMapping
    fun getStatus(): Mono<StatusResponse> = Mono.just(StatusResponse("WORKING"))

    @GetMapping("/sensors")
    fun getSensorStatus(): Mono<CmdStatusResponse> {
        val command = listOf("sensors")
        val rawLog = commandExecutorService.execute(command)
        return Mono.just(CmdStatusResponse("SUCCESS", rawLog))
    }

    @GetMapping("/process")
    fun getProcessStatus(): Mono<CmdStatusResponse> {
        val command = listOf<String>("ps aux")
        val rawLog = commandExecutorService.execute(command)
        return Mono.just(CmdStatusResponse("SUCCESS", rawLog))
    }

    @GetMapping("/process/{filterWord}")
    fun getProcessStatus(@PathVariable filterWord: String): Mono<CmdStatusResponse> {
        val command = listOf<String>("ps aux | grep $filterWord")
        val rawLog = commandExecutorService.execute(command)
        return Mono.just(CmdStatusResponse("SUCCESS", rawLog))
    }

    @GetMapping("/memory")
    fun getMemoryInfo(): Mono<CmdStatusResponse> {
        val command = listOf<String>("free -m")
        val rawLog = commandExecutorService.execute(command)
        return Mono.just(CmdStatusResponse("SUCCESS", rawLog))
    }

    @GetMapping("/memory/detailed")
    fun getDetailedMemoryInfo(): Mono<CmdStatusResponse> {
        val command = listOf<String>("cat /proc/meminfo")

        LOG.debug("Executing BASH command:\n   $command")
        val commands = listOf<String>("bash", "-c") + command
        val process = ProcessBuilder().command(commands).start()
        val rawLog: MutableList<String> = ArrayList()
        try {
            val b = BufferedReader(InputStreamReader(process.inputStream))
            var line: String? = ""
            while (b.readLine().also { line = it } != null) {
                rawLog.add(line!!)
//                println(line)
                LOG.debug(line)
            }
            b.close()
        } catch (e: Exception) {
            LOG.error("Failed to execute bash with command: $command")
            e.printStackTrace()
            throw ServerError("Failed to execute bash with command: $command", e)
        }

        return Mono.just(CmdStatusResponse("SUCCESS", rawLog))
    }
}