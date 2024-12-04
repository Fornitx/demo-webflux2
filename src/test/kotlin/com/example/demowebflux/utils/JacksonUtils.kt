package com.example.demowebflux.utils

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

private val staticMapper = jacksonObjectMapper()
    .findAndRegisterModules()
    .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)

private val prettyPrintWriter = staticMapper.writerWithDefaultPrettyPrinter()

fun tryPrettyPrint(str: String?): String? = if (str == null)
    null
else try {
    prettyPrintWriter.writeValueAsString(staticMapper.readValue<Any>(str))
} catch (_: Exception) {
    str
}
