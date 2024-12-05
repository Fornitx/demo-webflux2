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
    fun testJakartaValidation() {
        contextRunner()
            .withPropertyValues("demo.validation.int-jakarta=-1")
            .runLogging { context ->
                assertThat(context).hasFailed()
                    .failure.rootCause().hasMessageContaining("validation.intJakarta")
            }
    }

    @Test
    fun testHibernateValidation() {
        contextRunner()
            .withPropertyValues("demo.validation.int-hibernate=-1")
            .runLogging { context ->
                assertThat(context).hasFailed()
                    .failure.rootCause().hasMessageContaining("validation.intHibernate")
            }
    }
}
