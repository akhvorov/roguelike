
object Game {
    fun run() {
        var level = 1
        while (runLevel(level)) {
            level++
        }
    }

    private fun runLevel(level: Int): Boolean {
        var worldMap: WorldMap = MapGenerator.generateMap(level)

        return true
    }
}
