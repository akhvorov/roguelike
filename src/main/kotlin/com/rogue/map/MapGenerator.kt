package com.rogue.map

import com.rogue.GameConfig
import com.rogue.actor.PlayerActor
import com.rogue.actor.WallActor
import com.rogue.actor.enemy.EnemyActor
import com.rogue.utils.Point
import com.rogue.utils.on
import kotlin.random.Random

object MapGenerator {
    fun generateInitialMap(): LevelMap {
        val map = LevelMap(GameConfig.sizeX, GameConfig.sizeY)

        val generatedMap = generateInitialMap(GameConfig.sizeX, GameConfig.sizeY)
        for ((x, isWallArr) in generatedMap.withIndex()) {
            for ((y, isWall) in isWallArr.withIndex()) {
                if (isWall) {
                    map.add(x on y, WallActor.default)
                }
            }
        }

        map.add(map.getFree().random(), PlayerActor.default)

        EnemyActor.populateMap(map)

        return map
    }

    private fun generateInitialMap(height: Int, width: Int): MutableList<MutableList<Boolean>> {
        val map: MutableList<MutableList<Boolean>> = ArrayList()
        initMap(map, height, width)
        addPerimeterWalls(map, height, width)
        while (randomWalkWall(map, 0.1, 2)) {

        }
        return map
    }

    private fun initMap(map: MutableList<MutableList<Boolean>>, height: Int, width: Int) {
        for (i in 0 until height) {
            val horizontal: MutableList<Boolean> = ArrayList()
            for (j in 0 until width) {
                horizontal.add(false)
            }
            map.add(horizontal)
        }
    }

    private fun addPerimeterWalls(map: List<MutableList<Boolean>>, height: Int, width: Int) {
        for (i in 0 until height) {
            map[i][0] = true
            map[i][width - 1] = true
        }
        for (j in 0 until width) {
            map[0][j] = true
            map[height - 1][j] = true
        }
    }

    private fun randomWalkWall(map: List<MutableList<Boolean>>, pStop: Double, iterNum: Int = 10): Boolean {
        var prevPoint = selectPointForWallStart(map, iterNum) ?: return false
        while (Random.nextDouble() > pStop) {
            var iters = 0
            var nextPoint = randomNeighbourPoint(prevPoint, map)
            while (iters < iterNum && !map[nextPoint.x][nextPoint.y] && haveNotNeighbours(map, nextPoint.x, nextPoint.y)) {
                nextPoint = randomNeighbourPoint(prevPoint, map)
                iters++
            }
            map[prevPoint.x][prevPoint.y] = true
            if (iters == iterNum) {
                return false
            }
            prevPoint = nextPoint
        }
        return true
    }

    private fun randomNeighbourPoint(point: Point, map: List<List<Boolean>>): Point {
        while (true) {
            val rand = Random.nextInt(9)
            val newPoint = point + Point(rand / 3 - 1, rand % 3 - 1)
            if (point != newPoint && insideMap(map, newPoint.x, newPoint.y)) {
                return newPoint
            }
        }
    }

    private fun selectPointForWallStart(map: List<List<Boolean>>, iterNum: Int = 10): Point? {
        for (i in 0..iterNum) {
            val h = Random.nextInt(map.size)
            val w = Random.nextInt(map[h].size)
            if (!onEdge(map, h, w) && !haveNotNeighbours(map, h, w)) {
                return Point(h, w)
            }
        }
        return null
    }

    private fun haveNotNeighbours(map: List<List<Boolean>>, height: Int, width: Int): Boolean {
        var haveNeighbours = true
        for (h in height - 1..height + 1) {
            for (w in width - 1..width + 1) {
                if (h >= 0 && h < map.size && w >= 0 && w < map[h].size && !(h == height && w == width)) {
                    haveNeighbours = haveNeighbours && !map[h][w]
                }
            }
        }
        return haveNeighbours
    }

    private fun onEdge(map: List<List<Any>>, height: Int, width: Int): Boolean {
        return height == 0 || height == map.size - 1 || width == 0 || width == map[height].size - 1
    }

    private fun insideMap(map: List<List<Any>>, height: Int, width: Int): Boolean {
        return height >= 0 && height < map.size && width >= 0 && width < map[height].size
    }
}
