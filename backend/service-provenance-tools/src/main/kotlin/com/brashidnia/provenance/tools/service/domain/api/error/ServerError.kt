package com.brashidnia.provenance.tools.service.domain.api.error

class ServerError(message: String, cause: Throwable? = null) : Exception(message, cause)
