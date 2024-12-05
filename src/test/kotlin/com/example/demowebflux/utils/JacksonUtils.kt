package com.example.demowebflux.utils

import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

private val staticMapper = jacksonObjectMapper()
    .findAndRegisterModules()
    .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)

private val prettyPrintWriter = staticMapper.writer(DefaultPrettyPrinter().apply {
    indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE)
})

fun tryPrettyPrint(str: String?): String? = if (str == null)
    null
else try {
    prettyPrintWriter.writeValueAsString(staticMapper.readValue<Any>(str))
} catch (_: Exception) {
    str
}
