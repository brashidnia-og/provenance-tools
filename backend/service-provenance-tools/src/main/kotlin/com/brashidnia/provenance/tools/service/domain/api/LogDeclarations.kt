package com.brashidnia.provenance.tools.service.domain.api

import com.fasterxml.jackson.databind.JsonNode

class LogResponse(
    val status: String,
    val logs: List<JsonNode>
)
