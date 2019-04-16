package com.rogue

/**
 * Parent class for all drawable objects
 */
open class Drawable(var point: Point, var face: Char, val priority: Priority = Priority.WALL) : Comparable<Drawable> {
    /**
     * Priority of drawing and acting
     */
    enum class Priority {
        EMPTY,
        WALL,
        BONUS,
        SHOT,
        MONSTER,
        PLAYER
    }

    open fun draw() {
//        drawSymbolOnPoint(face)
    }

    override fun compareTo(other: Drawable): Int {
        return priority.compareTo(other.priority)
    }
}
