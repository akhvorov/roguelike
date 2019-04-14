package com.rogue.actors

import com.rogue.Drawable
import com.rogue.Point

class Actor(point: Point, face: Char, priority: Drawable.Priority,
            private val actionStrategy: (Actor, List<Actor>) -> Boolean) : Drawable(point, face, priority) {

    override fun draw() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun act(actors: List<Actor>) {

    }
}
