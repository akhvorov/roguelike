package com.rogue.actor.items

import com.rogue.GameConfig
import com.rogue.actor.Actor
import com.rogue.map.LevelMap

object Items {
    fun populateMap(levelMap: LevelMap) {
        for (i in 1..GameConfig.knives) {
            levelMap.add(levelMap.getFree().random(), Items.getKnife())
        }
        for (i in 1..GameConfig.armors) {
            levelMap.add(levelMap.getFree().random(), Items.getArmor())
        }
        for (i in 1..GameConfig.braves) {
            levelMap.add(levelMap.getFree().random(), EnemyStrategy.Brave.default())
        }
        for (i in 1..GameConfig.cowards) {
            levelMap.add(levelMap.getFree().random(), EnemyStrategy.Coward.default())
        }
        for (i in 1..GameConfig.others) {
            levelMap.add(levelMap.getFree().random(), EnemyStrategy.NotMyBusiness.default())
        }
    }

    private fun getArmor() = Actor(Actor.Type.ArmorInventory, Actor.Health(true, 1), 0, 'H')

    private fun getKnife() = Actor(Actor.Type.KnifeInventory, Actor.Health(true, 1), 0, '!')
}
