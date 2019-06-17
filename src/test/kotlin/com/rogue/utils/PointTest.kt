package com.rogue.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PointTest {
    @Test
    fun testCreateFromInts() {
        assertEquals(Point(1, 2), 1 on 2)
    }

    @Test
    fun testPlus() {
        assertEquals(Point(4,3), Point(1,2).plus(3 on 1))
    }

    @Test
    fun testApply() {
        assertEquals(Point(5,3), Point(5,2).apply(Move.DOWN))
    }

    @Test
    fun testMoveToOptions() {
        assertEquals(setOf(Move.RIGHT, Move.DOWN), Point(5,3).moveToOptions(Point(10, 10)))
    }
}