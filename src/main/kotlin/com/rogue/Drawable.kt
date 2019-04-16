package com.rogue
open class Drawable(var point: Point, var face: Char, val priority: Priority = Priority.WALL) : Comparable<Drawable> {
    enum class Priority {
        EMPTY,
        WALL,
        BONUS,
        SHOT,
        MONSTER,
        PLAYER
    }

    open fun draw() {
        drawSymbolOnPoint(face)
    }

    override fun compareTo(other: Drawable): Int {
        return priority.compareTo(other.priority)
    }

    private fun drawSymbolOnPoint(symbol: Char = face) {
        println("\u001B[${point.x};${point.y}H$symbol")
    }

//    fun getPoint(): Point {
//        return point
//    }
}
