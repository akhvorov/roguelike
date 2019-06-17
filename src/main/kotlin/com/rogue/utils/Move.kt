package com.rogue.utils

/**
 * Move direction
 */
enum class Move {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    STAY;

    /**
     * Move to inverted direction
     */
    val invert: Move
        get() = when (this) {
            UP -> DOWN
            DOWN -> UP
            LEFT -> RIGHT
            RIGHT -> LEFT
            STAY -> STAY
        }
}
