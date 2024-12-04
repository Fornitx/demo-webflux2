package com.example.demowebflux.rest

import com.example.demowebflux.constants.REST_PREFIX
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$REST_PREFIX/v1")
class DemoController(
    private val service: DemoService,
) {
    @GetMapping("/foo")
    fun foo(@RequestBody body: String): String = service.foo(body)
}