package com.example.demowebflux.logging

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientResponse

private val log = KotlinLogging.logger {}

object ClientHttpLogger {
    fun logRequest(request: ClientRequest) {
        val method = request.method()
        val url = request.url()

        if (log.isDebugEnabled()) {
            log.debug {
                val headers = request.headers().listHeaders()
                "Request $method $url\n$headers"
            }
        } else {
            log.info { "Request $method $url" }
        }
    }

    fun logResponse(response: ClientResponse) {
        val httpStatus = response.statusCode().value()

        if (log.isDebugEnabled()) {
            log.debug {
                val headers = response.headers().asHttpHeaders().listHeaders()
                "Response $httpStatus\n$headers"
            }
        } else {
            log.info { "Response $httpStatus" }
        }
    }

    private fun HttpHeaders.listHeaders(): String =
        this.map { "${it.key} : ${it.value}" }.joinToString(System.lineSeparator())
}
