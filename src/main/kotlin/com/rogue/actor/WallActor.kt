package com.rogue.actor

/**
 * Static class for wall creation
 */
object WallActor {
    /**
     * Default wall
     *
     * @return actor of wall
     */
    fun default() = Actor(Actor.Type.Wall, Actor.Health(false, 0), 0, '#')
}
