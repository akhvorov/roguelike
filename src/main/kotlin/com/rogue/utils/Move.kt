package com.rogue.utils

import java.util.*

/**
 * Move direction
 */
enum class Move {
    UP,
    RIGHT,
    DOWN,
    LEFT,
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

    fun nearest(): Set<Move> {
        if (this == STAY) return setOf(STAY)
        return HashSet(values().filter { Math.abs(it.ordinal % 4 - ordinal % 4) <= 1 && it != STAY })
    }
}
