package com.rogue.actor

import com.rogue.utils.Move

class WallActor : Actor(100, 0, '#') {
    override fun act() = Move.STAY
}
