package com.brashidnia.provenance.tools.service.domain.api

data class StatusResponse(
    val status: String
)

data class CmdStatusResponse(
    val status: String,
    val logs: List<String>
)
