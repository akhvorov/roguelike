package com.rogue.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MoveTest {
    @Test
    fun testInvert() {
        assertEquals(Move.DOWN, Move.UP.invert)
        assertEquals(Move.UP, Move.DOWN.invert)
        assertEquals(Move.LEFT, Move.RIGHT.invert)
        assertEquals(Move.RIGHT, Move.LEFT.invert)
    }
}
