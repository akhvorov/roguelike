package com.rogue.actors

import com.rogue.Drawable
import com.rogue.Point

class Player(point: Point) : Drawable(point, '4', Priority.PLAYER) {
    private var health: Int = 5

    override fun draw() {
        drawSymbolOnPoint(face)
    }

    class Strategy {
        
    }
}
