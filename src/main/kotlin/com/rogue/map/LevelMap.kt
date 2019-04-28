package com.rogue.map

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.rogue.actor.Actor
import com.rogue.draw.GameScreen
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
@JsonIgnoreProperties(value = ["free", "playerCell"])
data class LevelMap(val xSize: Int, val ySize: Int, val cells: ArrayList<Cell> = ArrayList()) {
    companion object {
        lateinit var current: LevelMap
    }

    data class Cell(var point: Point, val actor: Actor)

    operator fun get(point: Point): Cell? {
        return cells.find { it.point == point }
    }

    operator fun get(actor: Actor): Cell? {
        return cells.find { it.actor == actor }
    }

    fun getPlayerCell() = cells.find { it.actor.type == Actor.Type.Player }!!

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

        GameScreen.registerActor(point, actor)

        return true
    }

    fun reinitScreen() {
        for ((point, actor) in cells) {
            GameScreen.registerActor(point, actor)
        }
    }

    fun move(actor: Actor, move: Move): Boolean {
        val cell = get(actor)!!
        cell.point = cell.point.apply(move)
        GameScreen.moveActor(actor, move)

        return true
    }

    fun remove(actor: Actor): Boolean {
        val removed = cells.removeIf { it.actor == actor }
        if (removed) {
            GameScreen.removeActor(actor)
        }
        return removed
    }
}
