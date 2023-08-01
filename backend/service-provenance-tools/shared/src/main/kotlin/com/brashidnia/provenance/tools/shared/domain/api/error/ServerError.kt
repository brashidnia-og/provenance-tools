package com.brashidnia.provenance.tools.shared.domain.api.error

class ServerError(message: String, cause: Throwable? = null) : Exception(message, cause)
