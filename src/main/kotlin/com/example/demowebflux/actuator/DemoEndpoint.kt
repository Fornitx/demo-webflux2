package com.example.demowebflux.actuator

import org.springframework.boot.actuate.endpoint.annotation.Endpoint
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation
import org.springframework.stereotype.Component

@Component
@Endpoint(id = "demo")
class DemoEndpoint {
    @ReadOperation
    fun sample(): CustomData = CustomData("test", 5)

    data class CustomData(val str: String, val count: Int)
}
