package com.rogue

/**
 * Point of object position
 */
data class Point(var x: Int = 0, var y: Int = 0) : Comparable<Point> {
    override fun compareTo(other: Point): Int {
        if (x == other.x) {
            return y - other.y
        }
        return x - other.x
    }

    companion object {
        /**
         * Coordinate-wise addition of points
         *
         * @return result of addition of two points
         */
        fun add(p1: Point, p2: Point): Point {
            return Point(p1.x + p2.x, p1.y + p2.y)
        }
    }

    /**
     * Add other point to current
     */
    fun add(point: Point) {
        x += point.x
        y += point.y
    }
}
