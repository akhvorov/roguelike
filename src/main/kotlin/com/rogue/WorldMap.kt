package com.rogue

/**
 * Game world with map and actors
 */
class WorldMap(var map: List<MutableList<Boolean>>, var actors: MutableList<Actor>) {

    /**
     * Make step of all actors
     */
    fun act(): Boolean {
//        actors.sortBy { it.priority }
        var endGame = false
        for (i in 0 until actors.size) {
            if (actors[i].priority == Drawable.Priority.PLAYER) {
                endGame = actors[i].act(actors)
            } else {
                actors[i].act(actors)
            }
        }
        return endGame
    }

    /**
     * Draw the world map
     */
    fun draw() {
        for (line in buildMapView()) {
            println(line)
        }
    }

    /**
     * Build string represent state of map and actors
     *
     * @return list of strings with symbols of actors and walls
     */
    private fun buildMapView(): List<String> {
        val lines = ArrayList<String>()
        clearConsole(lines)
        val actorFaces = HashMap<Point, Char>()
        for (actor in actors) {
            actorFaces[actor.point] = actor.face
        }
        for (h in 0 until map.size) {
            var line = ""
            for (w in 0 until map[h].size) {
                line += if (map[h][w]) {
                    '0'
                } else if (actorFaces.containsKey(Point(h, w))) {
                    actorFaces[Point(h, w)]
                } else {
                    ' '
                }
            }
            lines.add(line)
        }
        return lines
    }

    /**
     * Clear console
     */
    private fun clearConsole(lines: MutableList<String>) {
//        jline.ConsoleReader
        for (i in 0..30) {
            lines.add("\n")
        }
    }
}
