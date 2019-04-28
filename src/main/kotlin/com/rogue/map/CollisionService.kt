package com.rogue.map

import com.rogue.actor.Actor
import com.rogue.utils.Move

object CollisionService {
    /**
     * Processes move and prepares level map.
     *
     * It will automatically reduce health of actors and remove them from the map,
     * if hp 0 or less.
     */
    fun processMove(actor: Actor, move: Move, levelMap: LevelMap): Move {
        if (move == Move.STAY) return move

        val point = levelMap[actor]!!.point

        val otherActor = levelMap[point.apply(move)]?.actor ?: return move

        if (!otherActor.health.isDestroyable) {
            return Move.STAY
        }

        return processMoveOnActor(actor, otherActor, move, levelMap)
    }

    private fun processMoveOnActor(fromActor: Actor, toActor: Actor, move: Move, map: LevelMap): Move {
        fromActor.health.hp -= toActor.damage
        toActor.health.hp -= fromActor.damage

        var resultMove: Move = move


        if (toActor.health.isDead) {
            map.remove(toActor)
        } else {
            resultMove = Move.STAY
        }
        if (fromActor.health.isDead) {
            map.remove(fromActor)
            resultMove = Move.STAY
        }

        return resultMove
    }
}
