package com.rogue.actor

import com.rogue.GameConfig
import com.rogue.map.LevelMap
import com.rogue.utils.Move

sealed class EnemyStrategy {
    abstract fun act(actor: Actor, levelMap: LevelMap): Move

    object Coward : EnemyStrategy() {
        fun default() = EnemyActor(Actor.Health(true, 5), 2, 'C', Coward)

        override fun act(actor: Actor, levelMap: LevelMap) = Brave.act(actor, levelMap).invert
    }

    object Brave : EnemyStrategy() {
        fun default() = EnemyActor(Actor.Health(true, 5), 1, 'B', Brave)

        override fun act(actor: Actor, levelMap: LevelMap): Move {
            val actorPoint = levelMap[actor]!!.point
            val playerPoint = levelMap.getPlayerCell().point
            return actorPoint.moveToOptions(playerPoint).random()
        }
    }

    object NotMyBusiness : EnemyStrategy() {
        fun default() = EnemyActor(Actor.Health(true, 5), 2, 'O', NotMyBusiness)


        override fun act(actor: Actor, levelMap: LevelMap) = Move.STAY
    }
}

class EnemyActor(health: Health, damage: Int, face: Char, private val strategy: EnemyStrategy) : Actor(health, damage, face) {
    companion object {
        fun populateMap(levelMap: LevelMap) {
            for (i in 1..GameConfig.braves) {
                val free = levelMap.getFree().random()
                levelMap.add(free, EnemyStrategy.Brave.default())
            }

            for (i in 1..GameConfig.cowards) {
                val free = levelMap.getFree().random()
                levelMap.add(free, EnemyStrategy.Coward.default())
            }

            for (i in 1..GameConfig.others) {
                val free = levelMap.getFree().random()
                levelMap.add(free, EnemyStrategy.NotMyBusiness.default())
            }
        }
    }

    override fun act(levelMap: LevelMap) = strategy.act(this, levelMap)
}
