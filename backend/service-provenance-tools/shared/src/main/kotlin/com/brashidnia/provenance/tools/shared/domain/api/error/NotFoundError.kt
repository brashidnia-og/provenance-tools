package com.brashidnia.provenance.tools.shared.domain.api.error

class NotFoundError(message: String, cause: Throwable? = null) : Exception(message, cause)
