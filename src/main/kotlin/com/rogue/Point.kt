package com.rogue

data class Point(var x: Int = 0, var y: Int = 0) {
    companion object {
        fun add(p1: Point, p2: Point): Point {
            return Point(p1.x + p2.x, p1.y + p2.y)
        }
    }
    fun add(point: Point) {
        x += point.x
        y += point.y
    }
}
