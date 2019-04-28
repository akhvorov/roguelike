package com.rogue.map

import com.rogue.actor.Actor
import com.rogue.actor.PlayerActor
import com.rogue.draw.MainScreen
import com.rogue.utils.*

/**
 * Map representing level.
 *
 * If points (x, y)
 * (0, 0)---(4, 0)
 * |
 * |
 * |
 * (0, 4)
 */
data class LevelMap(val xSize: Int, val ySize: Int, val cells: ArrayList<Cell> = ArrayList()) {
    data class Cell(var point: Point, val actor: Actor)

    operator fun get(point: Point): Cell? {
        return cells.find { it.point == point }
    }

    operator fun get(actor: Actor): Cell? {
        return cells.find { it.actor == actor }
    }

    fun getPlayerCell() = get(PlayerActor)!!

    fun getFree(): Set<Point> {
        val result = HashSet<Point>()
        for (i in 0 until xSize) {
            for (j in 0 until ySize) {
                if (!contains(i on j)) {
                    result += i on j
                }
            }
        }
        return result
    }


    fun contains(point: Point) = get(point) != null
    fun contains(actor: Actor) = get(actor) != null

    fun add(point: Point, actor: Actor): Boolean {
        require(point.x in 0 until xSize) { "Point exceeds borders of map by an x-axis" }
        require(point.y in 0 until xSize) { "Point exceeds borders of map by a y-axis" }

        if (contains(point)) return false

        cells.add(Cell(point, actor))

        if (actor is PlayerActor) {
            MainScreen.registerPlayer(point, actor)
        } else {
            MainScreen.registerActor(point, actor)
        }

        return true
    }

    fun move(actor: Actor, move: Move): Boolean {
        val cell = get(actor)!!
        cell.point = cell.point.apply(move)
        MainScreen.moveActor(actor, move)

        return true
    }

    fun remove(actor: Actor): Boolean {
        val removed = cells.removeIf { it.actor == actor }
        if (removed) {
            MainScreen.removeActor(actor)
        }
        return removed
    }
}
