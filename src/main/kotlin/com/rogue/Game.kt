package com.rogue

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
        val map = MapGenerator.generateInitialMap()

        while (true) {
            for (actor in map.cells.map { it.actor }.distinct()) {
                val proposedMove = actor.act()
                val realMove = CollisionService.processMove(actor, proposedMove, map)
                map.move(actor, realMove)
            }

            Thread.sleep(100)
        }
    }
}
