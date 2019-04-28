package com.rogue.actor

object WallActor {
    val default: Actor
        get() = Actor(Actor.Type.Wall, Actor.Health(false, 0), 0, '#')
}
