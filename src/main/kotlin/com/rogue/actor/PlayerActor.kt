package com.rogue.actor

import com.rogue.utils.Move
import java.util.*

object PlayerActor {
    val default by lazy { Actor(Actor.Type.Player, Actor.Health(true, 100), 2, '@') }

    private val lazyMovements = ArrayDeque<Move>()

    fun move(move: Move) = lazyMovements.push(move)

    fun act() = lazyMovements.poll() ?: Move.STAY
}
