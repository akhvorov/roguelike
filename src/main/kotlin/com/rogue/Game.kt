package com.rogue

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
        while (true) {
            worldMap.act()
            worldMap.draw()
            Thread.sleep(1000)
        }
        return true
    }
//
//    private fun strategies(map: MutableMap<Point, Drawable>): MutableList<Actor> {
//        val strategies = ArrayList<Actor>()
//        for (point in map.keys) {
//            if (map[point] is Actor) {
//                strategies.add(map[point] as Actor)
//                map.remove(point)
//            }
//        }
//        strategies.sort()
//        return strategies
//    }
}
