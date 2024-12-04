package com.example.demowebflux.metrics

import com.example.demowebflux.constants.METER_PREFIX
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class DemoMetrics(private val registry: MeterRegistry) {
    private fun counter(description: String, name: String, vararg tags: String): Counter =
        Counter.builder(name(name)).description(description).tags(*tags).register(registry)

    private fun timer(description: String, name: String, vararg tags: String): Timer =
        Timer.builder(name(name)).description(description).tags(*tags).register(registry)

    private fun gauge(
        description: String,
        name: String,
        vararg tags: String,
        numberSupplier: () -> Number
    ): Gauge = Gauge.builder(name(name), numberSupplier).description(description).tags(*tags).register(registry)

    companion object {
        private val nameCache = ConcurrentHashMap<String, String>()

        fun name(name: String): String = nameCache.computeIfAbsent(name) {
            METER_PREFIX + "_" + name
        }
    }
}