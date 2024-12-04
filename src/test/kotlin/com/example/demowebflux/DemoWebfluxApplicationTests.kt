package com.example.demowebflux

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.TimeUnit

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DemoWebfluxApplicationTests {
	@Test
	@Disabled
	fun contextLoads() {
		TimeUnit.HOURS.sleep(1)
	}
}
