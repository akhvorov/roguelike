package com.rogue

fun main() {
    Game.run()
}

object Game {
    fun run() {
        var level = 1
        while (runLevel(level)) {
            level++
        }
        println("Game over")
    }

    private fun runLevel(level: Int): Boolean {
        val worldMap = MapGenerator.generateWorld(level)
        worldMap.draw()
        while (worldMap.act()) {
            worldMap.draw()
            Thread.sleep(1000)
        }
        return true
    }
}
