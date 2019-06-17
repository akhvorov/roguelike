package com.rogue.map

import com.rogue.GameConfig
import com.rogue.actor.PlayerActor
import com.rogue.actor.WallActor
import com.rogue.actor.items.Items
import com.rogue.utils.on

/**
 * Map generator
 */
object MapGenerator {
    /**
     * Generate level map with actors
     *
     * @return level map
     */
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

        Items.populateMap(map)

        return map
    }
}
