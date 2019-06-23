package com.rogue.map

/**
 * Generator of maze (only walls)
 */
class MazeGenerator(private val x: Int, private val y: Int) {
    private val maze = Array(x) { IntArray(y) }

    /**
     * Generate a maze recursively
     *
     * @param cx a horizontal coordinate
     * @param cy a vertical coordinate
     */
    fun generate(cx: Int, cy: Int) {
        Direction.values().shuffle().forEach {
            val nx = cx + it.dx
            val ny = cy + it.dy
            if (between(nx, x) && between(ny, y) && maze[nx][ny] == 0) {
                maze[cx][cy] = maze[cx][cy] or it.bit
                maze[nx][ny] = maze[nx][ny] or it.opposite!!.bit
                generate(nx, ny)
            }
        }
    }

    /**
     * Get boolean 2D-array of walls in the maze
     *
     * @return maze
     */
    fun get(): MutableList<MutableList<Boolean>> {
        val map = ArrayList<MutableList<Boolean>>()
        for (i in 0 until y) {
            var list = ArrayList<Boolean>()
            for (j in 0 until x) {
                list.add(true)
                list.add(maze[j][i] and Direction.UP.bit == 0)
            }
            list.add(true)
            map.add(list)

            list = ArrayList()
            for (j in 0 until x) {
                list.add(maze[j][i] and Direction.LEFT.bit == 0)
                list.add(false)
            }
            list.add(true)
            map.add(list)
        }

        val list = ArrayList<Boolean>()
        for (j in 0 until x) {
            list.add(true)
            list.add(true)
        }
        list.add(true)
        map.add(list)
        return map
    }

    /**
     * Shuffle array elements
     *
     * @return a shuffled array
     */
    private inline fun <reified T> Array<T>.shuffle(): Array<T> {
        val list = toMutableList()
        list.shuffle()
        return list.toTypedArray()
    }

    /**
     * Direction with bit coding
     */
    private enum class Direction(val bit: Int, val dx: Int, val dy: Int) {
        UP(1, 0, -1),
        DOWN(2, 0, 1),
        RIGHT(4, 1, 0),
        LEFT(8, -1, 0);

        var opposite: Direction? = null

        companion object {
            init {
                UP.opposite = DOWN
                DOWN.opposite = UP
                RIGHT.opposite = LEFT
                LEFT.opposite = RIGHT
            }
        }
    }

    /**
     * Is number between zero and another integer
     *
     * @param v target number
     * @param v upper
     */
    private fun between(v: Int, upper: Int) = v in 0 until upper
}
