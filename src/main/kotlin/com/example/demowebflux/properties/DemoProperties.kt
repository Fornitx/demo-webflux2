package com.example.demowebflux.properties

import com.example.demowebflux.constants.PROPS_PREFIX
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Range
import org.hibernate.validator.constraints.time.DurationMax
import org.hibernate.validator.constraints.time.DurationMin
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import java.time.Duration

@ConfigurationProperties(PROPS_PREFIX, ignoreUnknownFields = false)
@Validated
data class DemoProperties(
    @field:Valid
    val validation: ValidationProperties,
    @field:Valid
    val rest: RestProperties,
)

data class ValidationProperties(
    @field:Min(1)
    val intJakarta: Int,
    @field:Range(min = 1, max = 3)
    val intHibernate: Int,
)

data class RestProperties(
    val enabled: Boolean,
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
    @field:NotBlank
    val url: String,

    val enableMetrics: Boolean,

    @field:DurationMin(millis = 100)
    @field:DurationMax(seconds = 60)
    val responseTimeout: Duration,

    @field:DurationMin(millis = 100)
    @field:DurationMax(seconds = 60)
    val connectionTimeout: Duration,

    @field:DurationMin(millis = 100)
    @field:DurationMax(seconds = 60)
    val readTimeout: Duration,

    @field:DurationMin(millis = 100)
    @field:DurationMax(seconds = 60)
    val writeTimeout: Duration,
)
