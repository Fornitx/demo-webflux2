package com.example.demowebflux.context

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BasicContextTest : AbstractContextTest() {
    @Test
    fun allOk() {
        contextRunner()
            .runLogging { context ->
                assertThat(context).hasNotFailed()
            }
    }

    @Test
    fun testServiceMultiplier() {
        contextRunner()
            .withPropertyValues("demo.service.multiplier=-1")
            .runLogging { context ->
                assertThat(context).hasFailed()
            }
    }

    @Test
    fun testClientMultiplier() {
        contextRunner()
            .withPropertyValues("demo.client.multiplier=-1")
            .runLogging { context ->
                assertThat(context).hasFailed()
            }
    }
}
