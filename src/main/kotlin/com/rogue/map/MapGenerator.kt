package com.rogue.map

import com.rogue.GameConfig
import com.rogue.actor.PlayerActor
import com.rogue.actor.WallActor
import com.rogue.actor.enemy.EnemyActor
import com.rogue.actor.inventory.Inventories
import com.rogue.utils.on

object MapGenerator {
    fun generateInitialMap(): LevelMap {
        val map = LevelMap(GameConfig.mapSizeX, GameConfig.mapSizeY)

        val mazeGenerator = MazeGenerator(GameConfig.mapSizeY / 2, GameConfig.mapSizeX / 2)
        mazeGenerator.generate(0, 0)
        val generatedMap = mazeGenerator.get()
        for ((x, isWallArr) in generatedMap.withIndex()) {
            for ((y, isWall) in isWallArr.withIndex()) {
                if (isWall) {
                    map.add(x on y, WallActor.default())
                }
            }
        }

        map.add(map.getFree().random(), PlayerActor.default)

        EnemyActor.populateMap(map)
        Inventories.populateMap(map)

        return map
    }
}
