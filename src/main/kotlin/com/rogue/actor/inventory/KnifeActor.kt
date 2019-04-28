package com.rogue.actor.inventory

import com.rogue.actor.Actor

object KnifeActor {
    fun default() = Actor(Actor.Type.KnifeInventory, Actor.Health(true, 1), 0, '!')
}
