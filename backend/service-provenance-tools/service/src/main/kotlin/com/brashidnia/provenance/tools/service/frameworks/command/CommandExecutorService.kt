package com.brashidnia.provenance.tools.service.frameworks.command

import com.brashidnia.provenance.tools.service.domain.api.error.ServerError
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader

@Service
class CommandExecutorService {
    companion object {
        val LOG = LoggerFactory.getLogger(CommandExecutorService::class.java.name)
    }

    fun execute(command: List<String>): List<String> {
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
        return rawLog
    }
}