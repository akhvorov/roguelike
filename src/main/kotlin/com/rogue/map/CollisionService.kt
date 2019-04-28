package com.rogue.map

import com.rogue.actor.Actor
import com.rogue.actor.WallActor
import com.rogue.utils.Move

object CollisionService {
    fun processMove(actor: Actor, move: Move, levelMap: LevelMap): Move {
        val point = levelMap[actor]!!.point

        val otherActor = levelMap[point.apply(move)]?.actor ?: return move

        if (otherActor is WallActor) {
            return Move.STAY
        }

        return move
    }
}
