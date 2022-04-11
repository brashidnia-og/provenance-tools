package com.brashidnia.provenance.tools.service.frameworks.web.error

import com.brashidnia.provenance.tools.service.domain.api.error.NotFoundError
import com.brashidnia.provenance.tools.service.domain.api.error.ServerError
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.reactive.handler.WebFluxResponseStatusExceptionHandler
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

/**
 * Used for error handling on Web Controller endpoints
 */
@Order(-2)
@ControllerAdvice
@RequestMapping(produces = [("application/json")])
class GlobalErrorHandler : WebFluxResponseStatusExceptionHandler() {

    companion object {
        val LOG = LoggerFactory.getLogger(GlobalErrorHandler::class.java.name)
    }

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        when (ex) {
            is NotFoundError -> exchange.response.statusCode = HttpStatus.NOT_FOUND
//            is ConflictError -> exchange.response.statusCode = HttpStatus.CONFLICT
//            is BadRequestError -> exchange.response.statusCode = HttpStatus.BAD_REQUEST
            is ServerError -> exchange.response.statusCode = HttpStatus.INTERNAL_SERVER_ERROR
            else -> exchange.response.statusCode = HttpStatus.INTERNAL_SERVER_ERROR
        }
        val message = ex.message ?: ex.javaClass.simpleName
        LOG.info(
            "${ex.javaClass.simpleName} treated as ${exchange.response.statusCode} for " +
                "${exchange.request.uri} failed with error $message",
            ex
        )

        return Mono.empty()
    }
}