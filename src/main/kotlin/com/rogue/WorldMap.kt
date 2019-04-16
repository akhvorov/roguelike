package com.rogue

class WorldMap(var map: MutableMap<Point, Drawable>, var actors: MutableList<Actor>) {

    fun act() {
        for (i in 0 until actors.size) {
            actors[i].act(actors)
        }
    }

    fun draw() {
        print("\u001Bc")
        for (drawable in map.values) {
            drawable.draw()
        }
        for (actor in actors) {
            actor.draw()
        }
    }
}
