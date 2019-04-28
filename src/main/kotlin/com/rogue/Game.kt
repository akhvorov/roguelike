package com.rogue

import com.rogue.actor.EnemyActor
import com.rogue.draw.MainScreen
import com.rogue.map.CollisionService
import com.rogue.map.MapGenerator

fun main() {
    Game.run()
}

object Game {
    fun run() {
        MainScreen.init()

        runLevel()
    }

    private fun runLevel() {
        val levelMap = MapGenerator.generateInitialMap()

        EnemyActor.populateMap(levelMap)

        while (true) {
            for (actor in levelMap.cells.map { it.actor }.distinct()) {
                if (!levelMap.contains(actor)) continue

                val proposedMove = actor.act(levelMap)
                val realMove = CollisionService.processMove(actor, proposedMove, levelMap)

                if (!levelMap.contains(actor)) continue
                levelMap.move(actor, realMove)
            }

            Thread.sleep(100)
        }
    }
}
