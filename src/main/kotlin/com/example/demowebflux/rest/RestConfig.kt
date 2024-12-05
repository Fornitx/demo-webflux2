package com.example.demowebflux.rest

import com.example.demowebflux.constants.PROPS_PREFIX
import com.example.demowebflux.properties.DemoProperties
import com.example.demowebflux.properties.RestProperties
import com.example.demowebflux.rest.client.DemoClient
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

private val log = KotlinLogging.logger {}

@ConditionalOnProperty("$PROPS_PREFIX.rest.enabled", havingValue = "true", matchIfMissing = false)
@Configuration
class RestConfig(properties: DemoProperties) {
    val properties: RestProperties = properties.rest

    @Bean
    fun demoService(): DemoService = DemoService(properties.service)

    @Bean
    fun demoClient(
        webClientBuilder: WebClient.Builder,
        objectMapper: ObjectMapper
    ): DemoClient {
        log.info { "WebClient created for url '${properties.client.url}'" }
        return DemoClient(webClientBuilder, properties.client, objectMapper)
    }
}