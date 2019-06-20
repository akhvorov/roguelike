package com.rogue.map

import com.rogue.actor.Actor
import com.rogue.actor.PlayerActor
import com.rogue.utils.Move
import com.rogue.utils.Point
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LevelMapTest {
    private val size = 5
    private val actor = Actor(Actor.Type.BraveEnemy, Actor.Health(true, 2), 1, 'a')
    private var map = LevelMap(size, size)

    @BeforeEach
    fun initMap() {
        map = LevelMap(size, size)
    }

    @Test
    fun testGetByPointEmpty() {
        val cell = map[Point(1, 2)]
        assertNull(cell)
    }

    @Test
    fun testGetByPointNotEmpty() {
        val point = Point(1, 2)
        map.add(point, actor)
        val cell = map[point]
        assertEquals(actor, cell!!.actor)
    }

    @Test
    fun testAdd() {
        val point = Point(1, 2)
        map.add(point, actor)
        val cell = map[point]
        assertEquals(actor, cell!!.actor)
    }

    @Test
    fun testGetByActor() {
        val point = Point(1, 2)
        map.add(point, actor)
        val cell = map[actor]
        assertEquals(point, cell!!.point)
    }

    @Test
    fun testGetPlayerCell() {
        val point = Point(1, 2)
        val actor = PlayerActor.default
        map.add(point, actor)
        val cell = map.getPlayerCell()
        assertEquals(actor, cell.actor)
    }

    @Test
    fun testGetFree() {
        val point = Point(1, 2)
        map.add(point, actor)
        assertEquals(size * size - 1, map.getFree().size)
    }

    @Test
    fun testMove() {
        val point = Point(1, 2)
        map.add(point, actor)
        val startCell = map[point]
        assertNotNull(startCell)
        map.move(actor, Move.RIGHT)
        val finishCell = map[Point(2, 2)]
        assertNotNull(finishCell)
        assertEquals(actor, finishCell!!.actor)
    }

    @Test
    fun testRemove() {
        val point = Point(1, 2)
        map.add(point, actor)
        val startCell = map[point]
        assertNotNull(startCell)
        assertEquals(actor, startCell!!.actor)
        map.remove(actor)
        val finishCell = map[point]
        assertNull(finishCell)
    }
}