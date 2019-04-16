package com.rogue

class Actor(point: Point, face: Char, priority: Drawable.Priority,
            private val actionStrategy: (Actor, List<Actor>) -> Boolean) : Drawable(point, face, priority) {

    fun act(actors: List<Actor>): Boolean {
        return actionStrategy(this, actors)
    }
}
