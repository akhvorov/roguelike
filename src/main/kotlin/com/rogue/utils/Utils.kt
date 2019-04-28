package com.rogue.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import kotlin.reflect.KClass

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    fun apply(move: Move) = when (move) {
        Move.UP -> this + (0 on -1)
        Move.DOWN -> this + (0 on 1)
        Move.LEFT -> this + (-1 on 0)
        Move.RIGHT -> this + (1 on 0)
        Move.STAY -> this
    }

    fun moveToOptions(point: Point): Collection<Move> {
        val options = HashSet<Move>()
        if (point.x < x) options += Move.LEFT
        if (point.x > x) options += Move.RIGHT
        if (point.y < y) options += Move.UP
        if (point.y > y) options += Move.DOWN
        return options
    }
}

infix fun Int.on(other: Int) = Point(this, other)

enum class Move {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    STAY;

    val invert: Move
        get() = when (this) {
            UP -> DOWN
            DOWN -> UP
            LEFT -> RIGHT
            RIGHT -> LEFT
            STAY -> STAY
        }
}

abstract class JsonUtilsHelper(val jsonMapper: ObjectMapper) {
    fun writeValueAsString(obj: Any): String = jsonMapper.writeValueAsString(obj)

    fun <T : Any> readValue(serialized: String, klass: KClass<T>): T = jsonMapper.readValue<T>(serialized, klass.java)
    inline fun <reified T : Any> readValue(serialized: String): T = readValue(serialized, T::class)

    inline fun <reified T : Any> readValue(serialized: String, typeInfo: TypeReference<T>): T = jsonMapper.readValue(serialized, typeInfo)

    inline fun <reified E : Collection<T>, reified T : Any> readCollectionValue(serialized: String): E {
        val type = TypeFactory.defaultInstance()
        return jsonMapper.readValue(serialized, type.constructCollectionType(E::class.java, T::class.java))
    }

    inline fun <reified E : Map<T, K>, reified T : Any, reified K : Any> readMapValue(serialized: String): E {
        val type = TypeFactory.defaultInstance()
        return jsonMapper.readValue(serialized, type.constructMapType(E::class.java, T::class.java, K::class.java))
    }
}

