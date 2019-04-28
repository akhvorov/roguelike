package com.rogue.utils

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.module.kotlin.KotlinModule

object Json : JsonUtilsHelper(ObjectMapper().also {
    it.registerModule(KotlinModule())
    it.dateFormat = StdDateFormat().withColonInTimeZone(true)
    it.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    it.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    it.setSerializationInclusion(JsonInclude.Include.NON_NULL)
})
