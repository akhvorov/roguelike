package com.rogue.actor

import com.rogue.map.LevelMap
import com.rogue.utils.Move

class WallActor : Actor(Health(false, 0), 0, '#') {
    override fun act(levelMap: LevelMap) = Move.STAY
}
