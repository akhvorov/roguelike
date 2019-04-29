package com.rogue.utils

import kotlinx.serialization.Serializable

@Serializable
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



