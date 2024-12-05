package com.example.demowebflux.rest

import com.example.demowebflux.properties.ServiceProperties
import io.github.oshai.kotlinlogging.KotlinLogging

private val log = KotlinLogging.logger {}

class DemoService(private val properties: ServiceProperties) {
    suspend fun foo(str: String): String {
        log.info { "Service called with '$str'" }
        return str.repeat(properties.multiplier)
    }
}
