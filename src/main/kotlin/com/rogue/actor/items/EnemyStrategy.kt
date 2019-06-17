package com.rogue.actor.items

import com.rogue.actor.Actor
import com.rogue.map.LevelMap
import com.rogue.utils.Move

/**
 * Enemy strategy for acting
 */
sealed class EnemyStrategy {
    /**
     * Make action
     *
     * @param actor actor to act
     * @param levelMap level map where actor live
     * @return direction move
     */
    abstract fun act(actor: Actor, levelMap: LevelMap): Move

    /**
     * Coward enemy strategy
     */
    object Coward : EnemyStrategy() {
        fun default() = Actor(Actor.Type.CowardEnemy, Actor.Health(true, 5), 2, 'C')

        override fun act(actor: Actor, levelMap: LevelMap) = Brave.act(actor, levelMap).invert
    }

    /**
     * Brave enemy strategy
     */
    object Brave : EnemyStrategy() {
        fun default() = Actor(Actor.Type.BraveEnemy, Actor.Health(true, 5), 1, 'B')

        override fun act(actor: Actor, levelMap: LevelMap): Move {
            val actorPoint = levelMap[actor]!!.point
            val playerPoint = levelMap.getPlayerCell().point
            return actorPoint.moveToOptions(playerPoint).random()
        }
    }

    /**
     * Not acting strategy
     */
    object NotMyBusiness : EnemyStrategy() {
        fun default() = Actor(Actor.Type.OtherEnemy, Actor.Health(true, 5), 2, 'O')


        override fun act(actor: Actor, levelMap: LevelMap) = Move.STAY
    }
}
