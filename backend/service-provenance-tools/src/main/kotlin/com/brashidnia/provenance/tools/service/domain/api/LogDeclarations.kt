package com.brashidnia.provenance.tools.service.domain.api

class LogResponse(
    val status: String,
    val logs: List<Any>
)

enum class LogFormat {
    STRING,
    JSON
}
