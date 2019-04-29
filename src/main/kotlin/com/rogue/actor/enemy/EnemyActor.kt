package com.rogue.actor.enemy

import com.rogue.GameConfig
import com.rogue.map.LevelMap


object EnemyActor {
    fun populateMap(levelMap: LevelMap) {
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
}
