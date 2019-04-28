package com.rogue.actor.enemy

import com.rogue.actor.Actor
import com.rogue.map.LevelMap
import com.rogue.utils.Move

sealed class EnemyStrategy {
    abstract fun act(actor: Actor, levelMap: LevelMap): Move

    object Coward : EnemyStrategy() {
        fun default() = Actor(Actor.Type.CowardEnemy, Actor.Health(true, 5), 2, 'C')

        override fun act(actor: Actor, levelMap: LevelMap) = Brave.act(actor, levelMap).invert
    }

    object Brave : EnemyStrategy() {
        fun default() = Actor(Actor.Type.BraveEnemy, Actor.Health(true, 5), 1, 'B')

        override fun act(actor: Actor, levelMap: LevelMap): Move {
            val actorPoint = levelMap[actor]!!.point
            val playerPoint = levelMap.getPlayerCell().point
            return actorPoint.moveToOptions(playerPoint).random()
        }
    }

    object NotMyBusiness : EnemyStrategy() {
        fun default() = Actor(Actor.Type.OtherEnemy, Actor.Health(true, 5), 2, 'O')


        override fun act(actor: Actor, levelMap: LevelMap) = Move.STAY
    }
}
