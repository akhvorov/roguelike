package com.rogue.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

/**
 * Json serialization utils
 */
object Json {
    val jsonMapper = Json(JsonConfiguration.Stable)

    fun writeValueAsString(serializer: KSerializer<Any>, obj: Any): String = jsonMapper.stringify(serializer, obj)

    inline fun <reified T : Any> readValue(serializer: KSerializer<T>, serialized: String): T = jsonMapper.parse(serializer, serialized)
}
