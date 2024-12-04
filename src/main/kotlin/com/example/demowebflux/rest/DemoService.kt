package com.example.demowebflux.rest

import com.example.demowebflux.properties.DemoProperties
import org.springframework.stereotype.Service

@Service
class DemoService(private val properties: DemoProperties) {
    fun foo(str: String): String = str.repeat(properties.service.multiplier)
}
