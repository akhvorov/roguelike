package com.rogue

/**
 * Actor class for objects, which can act
 */
class Actor(point: Point, face: Char, priority: Drawable.Priority,
            val actionStrategy: (Actor, List<Actor>) -> Boolean) : Drawable(point, face, priority) {

    /**
     * Make one step action
     */
    fun act(actors: List<Actor>): Boolean {
        return actionStrategy(this, actors)
    }
}
