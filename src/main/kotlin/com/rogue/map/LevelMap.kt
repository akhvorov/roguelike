package com.rogue.map

import com.rogue.actor.Actor
import com.rogue.draw.GameScreen
import com.rogue.utils.*
import kotlinx.serialization.Serializable

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
@Serializable
data class LevelMap(val xSize: Int, val ySize: Int, val cells: ArrayList<Cell> = ArrayList()) {
    companion object {
        lateinit var current: LevelMap
    }

    /**
     * Cell for a pair of Actor and Point
     */
    @Serializable
    data class Cell(var point: Point, val actor: Actor)

    /**
     * Get cell with provided point
     *
     * @param point target point
     * @return cell with this point
     */
    operator fun get(point: Point): Cell? {
        return cells.find { it.point == point }
    }

    /**
     * Get cell with provided actor
     *
     * @param actor target actor
     * @return cell with this actor
     */
    operator fun get(actor: Actor): Cell? {
        return cells.find { it.actor == actor }
    }

    /**
     * Get cell with a player
     *
     * @return cell with a player
     */
    fun getPlayerCell() = cells.find { it.actor.type == Actor.Type.Player }!!

    /**
     * Free points on a map
     *
     * @return set of free points
     */
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

    /**
     * Add a cell on point with an actor
     *
     * @param point position of new cell
     * @param actor actor for this cell
     * @return is a new cell added
     */
    fun add(point: Point, actor: Actor): Boolean {
        require(point.x in 0 until xSize) { "Point exceeds borders of map by an x-axis" }
        require(point.y in 0 until ySize) { "Point exceeds borders of map by a y-axis" }

        if (contains(point)) return false

        cells.add(Cell(point, actor))

        GameScreen.registerActor(point, actor)

        return true
    }

    /**
     * Register cells again
     */
    fun reinitScreen() {
        for ((point, actor) in cells) {
            GameScreen.registerActor(point, actor)
        }
    }

    /**
     * Move actor to some direction
     *
     * @param actor actor for this cell
     * @param move direction of move
     * @return true
     */
    fun move(actor: Actor, move: Move): Boolean {
        val cell = get(actor)!!
        cell.point = cell.point.apply(move)
        GameScreen.moveActor(actor, move)

        return true
    }

    /**
     * Remove cell with provided actor
     *
     * @param actor actor for remove
     * @return is a cell with actor removed
     */
    fun remove(actor: Actor): Boolean {
        val removed = cells.removeIf { it.actor == actor }
        if (removed) {
            GameScreen.removeActor(actor)
        }
        return removed
    }
}
