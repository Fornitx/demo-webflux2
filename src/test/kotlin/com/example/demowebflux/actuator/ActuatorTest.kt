package com.example.demowebflux.actuator

import com.example.demowebflux.AbstractTest
import com.example.demowebflux.utils.tryPrettyPrint
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalManagementPort
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ActuatorTest : AbstractTest() {
    @LocalManagementPort
    private var port: Int = 0

    @Test
    fun root() {
        val body = RestClient.create("http://localhost:$port")
            .get()
            .uri("/actuator")
            .retrieve()
            .body<String>()

        log.info { "/actuator body:\n${tryPrettyPrint(body)}" }
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "configprops",
            "demo",
            "env",
            "health", "health/liveness", "health/readiness",
            "info",
            "metrics",
            "prometheus",
            "scheduledtasks"
        ]
    )
    fun links(link: String) {
        val body = RestClient.create("http://localhost:$port")
            .get()
            .uri("/actuator/$link")
            .retrieve()
            .body<String>()

        log.info { "/actuator/$link body:\n${tryPrettyPrint(body)}" }
    }
}