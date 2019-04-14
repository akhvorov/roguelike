package com.rogue

import com.rogue.actors.Player
import kotlin.math.sqrt
import kotlin.random.Random

object MapGenerator {
    fun generateMap(level: Int): WorldMap {
        val height = Random.nextInt((7 * sqrt(level.toDouble() + 1)).toInt())
        val width = Random.nextInt((10 * sqrt(level.toDouble() + 1)).toInt())
        val map = generateWalls(height, width)
        addPlayer(map)
        return WorldMap(mapToDrawable(map))
    }

    private fun mapToDrawable(map: MutableList<MutableList<Drawable.Priority>>): MutableMap<Point, Drawable> {
        val worldMap: MutableMap<Point, Drawable> = HashMap()
        for (h in (1..map.size)) {
            for (w in (1..map[h].size)) {
                val p = Point(h, w)
                if (map[h][w] != Drawable.Priority.EMPTY) {
                    worldMap[p] = when (map[h][w]) {
                        Drawable.Priority.WALL -> Drawable(p, '0', map[h][w])
//                        com.rogue.Drawable.Priority.BONUS -> TODO()
//                        com.rogue.Drawable.Priority.SHOT -> TODO()
//                        com.rogue.Drawable.Priority.MONSTER -> TODO()
                        Drawable.Priority.PLAYER -> Player(p)
                        else -> Drawable(p, ' ', map[h][w])
                    }
                }
            }
        }
        return worldMap
    }

    private fun addPlayer(map: MutableList<MutableList<Drawable.Priority>>) {
        val point = randomEmptyPoint(map) ?: return
        map[point.x][point.y] = Drawable.Priority.PLAYER
    }

    private fun randomEmptyPoint(map: MutableList<MutableList<Drawable.Priority>>): Point? {
        var num = 0
        while (num < map.size * map[0].size) {
            val h = Random.nextInt(map.size)
            val w = Random.nextInt(map[h].size)
            if (map[h][w] <= Drawable.Priority.EMPTY) {
                return Point(h, w)
            }
            num++
        }
        return null
    }

    private fun generateWalls(height: Int, width: Int): MutableList<MutableList<Drawable.Priority>> {
        val map: MutableList<MutableList<Drawable.Priority>> = ArrayList()
        initMap(map, height, width)
        addPerimeterWalls(map, height, width)
        while (randomWalkWall(map, 0.1, 10)) {

        }
        return map
    }

    private fun initMap(map: MutableList<MutableList<Drawable.Priority>>, height: Int, width: Int) {
        for (i in (0..height)) {
            val horizontal: MutableList<Drawable.Priority> = ArrayList()
            for (j in (0..width)) {
                horizontal.add(Drawable.Priority.EMPTY)
            }
            map.add(horizontal)
        }
    }

    private fun addPerimeterWalls(map: List<MutableList<Drawable.Priority>>, height: Int, width: Int) {
        for (i in (0..height)) {
            map[i][0] = Drawable.Priority.WALL
            map[i][width - 1] = Drawable.Priority.WALL
        }
        for (j in (0..width)) {
            map[0][j] = Drawable.Priority.WALL
            map[height - 1][j] = Drawable.Priority.WALL
        }
    }

    private fun randomWalkWall(map: List<MutableList<Drawable.Priority>>, pStop: Double, triesNum: Int = 10): Boolean {
        var prevPoint = selectPointForWallStart(map, triesNum) ?: return false
        while (Random.nextDouble() > pStop) {
            var tries = 0
            var rand = Random.nextInt(9)
            var nextPoint = Point.add(prevPoint, Point(rand / 3, rand % 3))
            while ((rand == 4 || !haveNotNeighbours(map, nextPoint.x, nextPoint.y)) && triesNum < 9) {
                rand = Random.nextInt(9)
                nextPoint = Point.add(prevPoint, Point(rand / 3, rand % 3))
                tries++
            }
            map[prevPoint.x][prevPoint.y] = Drawable.Priority.WALL
            if (tries == triesNum) {
                return false
            }
            prevPoint = nextPoint
        }
        return true
    }

    private fun selectPointForWallStart(map: List<List<Drawable.Priority>>, triesNum: Int = 10): Point? {
        for (i in (0..triesNum)) {
            val h = Random.nextInt(map.size)
            val w = Random.nextInt(map[h].size)
            if (haveNotNeighbours(map, h, w) || onEdge(map, h, w)) {
                return Point(h, w)
            }
        }
        return null
    }

    private fun haveNotNeighbours(map: List<List<Drawable.Priority>>, height: Int, width: Int): Boolean {
        var haveNeighbours = false
        for (h in (height-1..height+1)) {
            for (w in (width-1..width+1)) {
                if (h >= 0 && h < map.size && w >= 0 && w < map[h].size && !(h == height && w == width)) {
                    haveNeighbours = haveNeighbours || map[h][w] <= Drawable.Priority.EMPTY
                }
            }
        }
        return haveNeighbours
    }

    private fun onEdge(map: List<List<Any>>, height: Int, width: Int): Boolean {
        return height == 0 || height == map.size - 1 || width == 0 || width == map[height].size - 1
    }
}
