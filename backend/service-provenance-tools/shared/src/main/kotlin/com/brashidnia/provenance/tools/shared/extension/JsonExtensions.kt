package com.brashidnia.provenance.tools.shared.extension

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

private val defaultMapper = ObjectMapper().findAndRegisterModules().registerKotlinModule()
    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

fun Any.toJsonString(): String = defaultMapper.writer()
    .withDefaultPrettyPrinter()
    .writeValueAsString(this)

fun Any.toPrettyJson(): String = defaultMapper.writer(
    DefaultPrettyPrinter().also {
        it.indentArraysWith(DefaultIndenter().withIndent("  "))
    }
).writeValueAsString(this)
