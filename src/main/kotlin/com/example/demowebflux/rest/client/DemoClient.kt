package com.example.demowebflux.rest.client

import com.example.demowebflux.constants.METRICS_PREFIX
import com.example.demowebflux.logging.ClientHttpLogger
import com.example.demowebflux.properties.ClientProperties
import com.example.demowebflux.rest.client.data.DemoClientRequest
import com.example.demowebflux.rest.client.data.DemoClientResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.oshai.kotlinlogging.KotlinLogging
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.http.HttpStatus
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.CoExchangeFilterFunction
import org.springframework.web.reactive.function.client.CoExchangeFunction
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.function.client.awaitEntity
import org.springframework.web.reactive.function.client.awaitExchangeOrNull
import reactor.netty.channel.MicrometerChannelMetricsRecorder
import reactor.netty.http.client.HttpClient
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import java.util.function.Supplier

private val log = KotlinLogging.logger {}

class DemoClient(
    private val webClientBuilder: WebClient.Builder,
    private val properties: ClientProperties,
    private val objectMapper: ObjectMapper,
) {
    private val webClient: WebClient

    init {
        val httpClient = HttpClient.create()
            .responseTimeout(properties.responseTimeout)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, properties.connectionTimeout.toMillis().toInt())
            .doOnConnected { conn ->
                conn.addHandler(ReadTimeoutHandler(properties.readTimeout.toMillis(), TimeUnit.MILLISECONDS))
                conn.addHandler(WriteTimeoutHandler(properties.writeTimeout.toMillis(), TimeUnit.MILLISECONDS))
            }
            .metrics(
                properties.enableMetrics,
                Supplier { MicrometerChannelMetricsRecorder("${METRICS_PREFIX}democlient", "") }
            )
//            .wiretap("reactor.netty.http.client.HttpClient", LogLevel.INFO, AdvancedByteBufFormat.TEXTUAL)
        webClient = webClientBuilder.baseUrl(properties.url)
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .filters {
                it.add(logRequest())
                it.add(logResponse())
            }
            .build()
    }

    suspend fun call(request: DemoClientRequest): DemoClientResponse? {
        val entity = webClient.post()
            .bodyValue(request)
            .awaitExchangeOrNull { response -> response.awaitEntity<String>() }
        if (entity == null) {
            log.warn { "Entity is null" }
            return null
        }
        if (!entity.statusCode.isSameCodeAs(HttpStatus.OK)) {
            throw WebClientResponseException.create(
                entity.statusCode.value(),
                entity.statusCode.toString(),
                entity.headers,
                entity.body?.encodeToByteArray() ?: byteArrayOf(),
                Charset.defaultCharset()
            )
        }
        val body = entity.body
        if (body == null) {
            log.warn { "Entity body is null" }
            return null
        }
        return objectMapper.readValue<DemoClientResponse>(body)
    }

    private fun logRequest(): CoExchangeFilterFunction = object : CoExchangeFilterFunction() {
        override suspend fun filter(request: ClientRequest, next: CoExchangeFunction): ClientResponse {
            ClientHttpLogger.logRequest(request)
            return next.exchange(request)
        }
    }

    private fun logResponse(): CoExchangeFilterFunction = object : CoExchangeFilterFunction() {
        override suspend fun filter(request: ClientRequest, next: CoExchangeFunction): ClientResponse {
            val response = next.exchange(request)
            // https://github.com/spring-projects/spring-framework/issues/32148
//            withLoggingContext(
//                LOGSTASH_REQUEST_ID to coroutineContext[ReactorContext]!!.context[ATTRIBUTE_REQUEST_ID]
//            ) {
            ClientHttpLogger.logResponse(response)
//            }
            return response
        }
    }
}
