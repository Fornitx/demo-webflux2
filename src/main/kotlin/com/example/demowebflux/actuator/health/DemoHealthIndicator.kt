package com.example.demowebflux.actuator.health

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.ReactiveHealthIndicator
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class DemoHealthIndicator : ReactiveHealthIndicator {
    override fun health(): Mono<Health> = Mono.just(Health.up().build())
}
