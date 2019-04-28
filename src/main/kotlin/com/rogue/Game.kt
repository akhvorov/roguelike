package com.rogue

import com.rogue.draw.*
import com.rogue.map.CollisionService
import com.rogue.map.LevelMap
import com.rogue.state.StateService

fun main() {
    Game.eventLoop()
}

object Game {
    var isMenu = true

    fun eventLoop() {
        while (true) {
            if (isMenu) {
                Application.menuScreen.display()
                runMenu()
            } else {
                Application.gameScreen.display()
                runLevel()
            }
        }
    }

    private fun runMenu() {
        MenuScreen.init()
        MenuScreen.display()
        while (isMenu) {
            Thread.sleep(1000)
        }
    }

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

            Thread.sleep(100)
        }
    }
}
