package com.example.demowebflux.rest

import com.example.demowebflux.AbstractTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.test.web.reactive.server.WebTestClient

@AutoConfigureWebTestClient
abstract class AbstractWebTestClientTest : AbstractTest() {
    @Autowired
    protected lateinit var webTestClient: WebTestClient
}