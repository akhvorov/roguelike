package com.rogue.actor.inventory

import com.rogue.GameConfig
import com.rogue.map.LevelMap

object Inventories {
    fun populateMap(levelMap: LevelMap) {
        for (i in 1..GameConfig.knives) {
            levelMap.add(levelMap.getFree().random(), KnifeActor.default())
        }
        for (i in 1..GameConfig.armors) {
            levelMap.add(levelMap.getFree().random(), ArmorActor.default())
        }
    }
}
