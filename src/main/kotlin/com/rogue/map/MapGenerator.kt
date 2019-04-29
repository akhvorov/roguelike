package com.rogue.map

import com.rogue.GameConfig
import com.rogue.actor.PlayerActor
import com.rogue.actor.WallActor
import com.rogue.utils.on

object MapGenerator {
    fun generateInitialMap(): LevelMap {
        val map = LevelMap(GameConfig.sizeX, GameConfig.sizeY)

        val mazeGenerator = MazeGenerator(GameConfig.sizeX / 2, GameConfig.sizeY / 2)
        mazeGenerator.generate(0, 0)
        val generatedMap = mazeGenerator.get()
        for ((x, isWallArr) in generatedMap.withIndex()) {
            for ((y, isWall) in isWallArr.withIndex()) {
                if (isWall) {
                    map.add(x on y, WallActor())
                }
            }
        }

        map.add(1 on 1, PlayerActor)

        return map
    }
}
