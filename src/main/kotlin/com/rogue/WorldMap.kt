package com.rogue

class WorldMap(var map: List<MutableList<Boolean>>, var actors: MutableList<Actor>) {

    fun act() {
//        actors.sortBy { it.priority }
        for (i in 0 until actors.size) {
            actors[i].act(actors)
        }
    }

    fun draw() {
        for (line in buildMap()) {
            println(line)
        }
    }

    private fun buildMap(): List<String> {
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

    private fun clearConsole(lines: MutableList<String>) {
//        jline.ConsoleReader
        for (i in 0..30) {
            lines.add("\n")
        }
    }
}
