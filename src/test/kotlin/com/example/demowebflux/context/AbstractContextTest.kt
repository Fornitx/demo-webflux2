package com.example.demowebflux.context

import com.example.demowebflux.AbstractTest
import com.example.demowebflux.DemoWebfluxApplication
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer
import org.springframework.boot.test.context.assertj.AssertableReactiveWebApplicationContext
import org.springframework.boot.test.context.runner.ContextConsumer
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner
import org.springframework.context.ApplicationContext
import kotlin.collections.isNotEmpty
import kotlin.collections.joinToString
import kotlin.jvm.java

abstract class AbstractContextTest : AbstractTest() {
    protected fun contextRunner(): ReactiveWebApplicationContextRunner {
        return ReactiveWebApplicationContextRunner()
            .withInitializer(ConfigDataApplicationContextInitializer())
            .withUserConfiguration(DemoWebfluxApplication::class.java)
    }

    protected fun ReactiveWebApplicationContextRunner.withProfiles(vararg profiles: String): ReactiveWebApplicationContextRunner {
        require(profiles.isNotEmpty())
        return this.withPropertyValues("spring.profiles.active=${profiles.joinToString()}")
    }

    fun ReactiveWebApplicationContextRunner.runLogging(
        contextConsumer: ContextConsumer<AssertableReactiveWebApplicationContext>
    ) = this.run { context: AssertableReactiveWebApplicationContext ->
        if (context.startupFailure != null) {
            log.error(context.startupFailure) {}
        }
        contextConsumer.accept(context)
    }
}
