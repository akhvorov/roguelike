package com.rogue.utils

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
}

infix fun Int.on(other: Int) = Point(this, other)

enum class Move {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    STAY
}

