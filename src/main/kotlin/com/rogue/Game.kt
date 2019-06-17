package com.rogue

import com.rogue.draw.*
import com.rogue.map.CollisionService
import com.rogue.map.LevelMap
import com.rogue.state.StateService

/**
 * Game entry point
 */
fun main() {
    Game.eventLoop()
}

/**
 * Object for game launch
 */
object Game {
    var isMenu = true

    /**
     * Main event loop
     */
    fun eventLoop() {
        while (true) {
            if (isMenu) {
                MenuScreen.menuScreen.display()
                runMenu()
            } else {
                GameScreen.gameScreen.display()
                runLevel()
            }
        }
    }

    /**
     * Run menu of game
     */
    private fun runMenu() {
        MenuScreen.init()
        MenuScreen.display()
        while (isMenu) {
            Thread.sleep(1000)
        }
    }

    /**
     * Run level of game
     */
    private fun runLevel() {
        StateService.loadOrCreateMap()

        GameScreen.init()
        GameScreen.display()


        while (!isMenu) {
            for (actor in LevelMap.current.cells.map { it.actor }.distinct()) {
                if (!LevelMap.current.contains(actor)) continue

                val proposedMove = actor.act(LevelMap.current)
                val realMove = CollisionService.processMove(actor, proposedMove, LevelMap.current)

                if (!LevelMap.current.contains(actor)) continue
                LevelMap.current.move(actor, realMove)
            }

            GameScreen.updateHeroPanel()
            Thread.sleep(100)
        }
    }
}
