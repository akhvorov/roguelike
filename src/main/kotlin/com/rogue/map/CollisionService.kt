package com.rogue.map

import com.rogue.actor.Actor
import com.rogue.utils.Move

/**
 * Service for collisions manage
 */
object CollisionService {
    /**
     * Processes move and prepares level map.
     *
     * It will automatically reduce health of actors and remove them from the map,
     * if hp 0 or less.
     *
     * @param actor actor, which want to move
     * @param move direction of move
     * @param levelMap level map where actor live
     * @return direction to move
     */
    fun processMove(actor: Actor, move: Move, levelMap: LevelMap): Move {
        if (move == Move.STAY) return move
        val point = levelMap[actor]!!.point
        val otherActor = levelMap[point.apply(move)]?.actor ?: return move
        if (!otherActor.health.destroyable) {
            return Move.STAY
        }
        return processMoveOnActor(actor, otherActor, move, levelMap)
    }

    /**
     * Update state while move with neighbour actor
     */
    private fun processMoveOnActor(fromActor: Actor, toActor: Actor, move: Move, map: LevelMap): Move {
        fromActor.health.defaultHp -= toActor.damage
        toActor.health.defaultHp -= fromActor.damage

        var resultMove: Move = move


        if (toActor.isDead) {
            fromActor.stats.killed++
            toActor.affect(fromActor)
            map.remove(toActor)
        } else {
            resultMove = Move.STAY
        }
        if (fromActor.isDead) {
            toActor.stats.killed++
            map.remove(fromActor)
            resultMove = Move.STAY
        }

        return resultMove
    }
}
