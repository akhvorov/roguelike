package com.rogue.map

import com.rogue.utils.Move
import com.rogue.utils.Point
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.random.Random

class MazeGeneratorTest {
    @Test
    fun testGenerate() {
        val size = 10
        val start = 0
        val generator = MazeGenerator(size, size)
        generator.generate(start, start)
        val maze = generator.get()
        val targetPoint: Point
        while (true) {
            val point = Point(Random.Default.nextInt(size), Random.Default.nextInt(size))
            if (!maze[point.x][point.y]) {
                targetPoint = point
                break
            }
        }
        for (i in 0 until 100) {
            val point = Point(Random.Default.nextInt(size), Random.Default.nextInt(size))
            if (!maze[point.x][point.y]) {
                assertTrue(havePathToPlayer(maze, point, targetPoint))
            }
        }
    }

    /**
     * Check existence of path between two points
     *
     * @param maze boolean 2D-array with walls
     * @param fromPoint start point
     * @param toPoint end point
     * @return is a path between points exist
     */
    private fun havePathToPlayer(maze: List<List<Boolean>>, fromPoint: Point, toPoint: Point): Boolean {
        val queue = mutableListOf(fromPoint)
        val visitedPoints = mutableSetOf(fromPoint)
        while (queue.isNotEmpty()) {
            val point = queue[0]
            queue.removeAt(0)
            if (point == toPoint) {
                return true
            }
            visitedPoints.add(point)
            for (move in Move.values()) {
                val movedPoint = point.copy().apply(move)
                if (!visitedPoints.contains(movedPoint) && !maze[movedPoint.x][movedPoint.y]) {
                    queue.add(movedPoint)
                }
            }
        }
        return false
    }
}