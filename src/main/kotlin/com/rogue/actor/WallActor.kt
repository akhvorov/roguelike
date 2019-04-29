package com.rogue.actor

object WallActor {
    fun default() = Actor(Actor.Type.Wall, Actor.Health(false, 0), 0, '#')
}
