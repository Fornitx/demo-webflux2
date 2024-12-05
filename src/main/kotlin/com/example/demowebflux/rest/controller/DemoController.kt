package com.example.demowebflux.rest.controller

import com.example.demowebflux.constants.HEADER_X_REQUEST_ID
import com.example.demowebflux.constants.PROPS_PREFIX
import com.example.demowebflux.constants.REST_PREFIX
import com.example.demowebflux.rest.DemoService
import com.example.demowebflux.rest.controller.data.DemoServerRequest
import com.example.demowebflux.rest.controller.data.DemoServerResponse
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@ConditionalOnProperty("$PROPS_PREFIX.rest.enabled", havingValue = "true", matchIfMissing = false)
@RestController
@RequestMapping("${REST_PREFIX}/v1")
class DemoController(
    private val service: DemoService,
) {
    @GetMapping("/foo")
    suspend fun foo(
        @RequestHeader(HEADER_X_REQUEST_ID) requestId: String,
        @RequestBody body: DemoServerRequest
    ): DemoServerResponse = DemoServerResponse(service.foo(body.msg))
}