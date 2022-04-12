package com.brashidnia.provenance.tools.service.frameworks.web.api.v1

import com.brashidnia.provenance.tools.service.domain.api.CmdStatusResponse
import com.brashidnia.provenance.tools.service.domain.api.StatusResponse
import com.brashidnia.provenance.tools.service.domain.api.error.ServerError
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Compiler.command

@RestController
@RequestMapping("/api/v1/status")
class StatusController {
    companion object {
        val LOG = LoggerFactory.getLogger(StatusController::class.java.name)
    }

    @GetMapping
    fun getStatus(): StatusResponse {
        return StatusResponse("WORKING")
    }

    @GetMapping("/sensors")
    fun getSensorStatus(): CmdStatusResponse {
        val command = "sensors"
        LOG.debug("Executing BASH command:\n   $command")
        val r = Runtime.getRuntime()
        // Use bash -c so we can handle things like multi commands separated by ; and
        // things like quotes, $, |, and \. My tests show that command comes as
        // one argument to bash, so we do not need to quote it to make it one thing.
        // Also, exec may object if it does not have an executable file as the first thing,
        // so having bash here makes it happy provided bash is installed and in path.
        // Use bash -c so we can handle things like multi commands separated by ; and
        // things like quotes, $, |, and \. My tests show that command comes as
        // one argument to bash, so we do not need to quote it to make it one thing.
        // Also, exec may object if it does not have an executable file as the first thing,
        // so having bash here makes it happy provided bash is installed and in path.
        val commands = arrayOf<String>("bash", "-c", command)
        val rawLog: MutableList<String> = ArrayList()
        try {
            val p = r.exec(commands)
            p.waitFor()
            val b = BufferedReader(InputStreamReader(p.inputStream))
            var line: String? = ""
            while (b.readLine().also { line = it } != null) {
                rawLog.add(line!!)
                LOG.debug(line)
            }
            b.close()
        } catch (e: Exception) {
            LOG.error("Failed to execute bash with command: $command")
            e.printStackTrace()
            throw ServerError("Failed to execute bash with command: $command", e)
        }

        return CmdStatusResponse("SUCCESS", rawLog)
    }

    @GetMapping("/process")
    fun getProcessStatus(): CmdStatusResponse {
        val command = listOf<String>("ps aux")

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

        return CmdStatusResponse("SUCCESS", rawLog)
    }

    @GetMapping("/process/{filterWord}")
    fun getProcessStatus(@PathVariable filterWord: String): CmdStatusResponse {
        val command = listOf<String>("ps aux | grep $filterWord")

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

        return CmdStatusResponse("SUCCESS", rawLog)
    }

    @GetMapping("/memory")
    fun getMemoryInfo(): CmdStatusResponse {
        val command = listOf<String>("free -m")

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

        return CmdStatusResponse("SUCCESS", rawLog)
    }

    @GetMapping("/memory/detailed")
    fun getDetailedMemoryInfo(): CmdStatusResponse {
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

        return CmdStatusResponse("SUCCESS", rawLog)
    }
}