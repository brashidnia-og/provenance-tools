package com.brashidnia.provenance.tools.service.domain.api.error

class NotFoundError(message: String, cause: Throwable? = null) : Exception(message, cause)
