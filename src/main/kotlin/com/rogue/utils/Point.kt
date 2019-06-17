package com.rogue.utils

import kotlinx.serialization.Serializable

/**
 * 2D Point
 */
@Serializable
data class Point(val x: Int, val y: Int) {
    /**
     * Add point to this point
     *
     * @param other point to add
     */
    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    /**
     * Apply the move in neighbour point
     *
     * @param move Direction of move
     */
    fun apply(move: Move) = when (move) {
        Move.UP -> this + (0 on -1)
        Move.DOWN -> this + (0 on 1)
        Move.LEFT -> this + (-1 on 0)
        Move.RIGHT -> this + (1 on 0)
        Move.STAY -> this
    }

    /**
     * Moves to target point
     *
     * @param point target point
     */
    fun moveToOptions(point: Point): Collection<Move> {
        val options = HashSet<Move>()
        if (point.x < x) options += Move.LEFT
        if (point.x > x) options += Move.RIGHT
        if (point.y < y) options += Move.UP
        if (point.y > y) options += Move.DOWN
        return options
    }
}

/**
 * Point constructor by two integers
 */
infix fun Int.on(other: Int) = Point(this, other)
