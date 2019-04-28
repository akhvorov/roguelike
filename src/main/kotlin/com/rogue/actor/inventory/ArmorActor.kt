package com.rogue.actor.inventory

import com.rogue.actor.Actor

object ArmorActor {
    fun default() = Actor(Actor.Type.ArmorInventory, Actor.Health(true, 1), 0, 'H')
}
