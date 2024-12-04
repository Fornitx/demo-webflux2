package com.example.demowebflux.properties

import com.example.demowebflux.constants.PROPS_PREFIX
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.hibernate.validator.constraints.Range
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@ConfigurationProperties(PROPS_PREFIX, ignoreUnknownFields = false)
@Validated
data class DemoProperties(
    @field:Valid
    val service: ServiceProperties,
    @field:Valid
    val client: ClientProperties,
)

data class ServiceProperties(
    @field:Range(1, 3)
    val multiplier: Int,
)


data class ClientProperties(
    @field:Min(1)
    val multiplier: Int,
)
