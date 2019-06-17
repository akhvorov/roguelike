package com.rogue.actor

import com.rogue.utils.Move
import java.util.*

/**
 * Player actor
 */
object PlayerActor {
    val default by lazy { Actor(Actor.Type.Player, Actor.Health(true, 100), 2, '@') }

    private val lazyMovements = ArrayDeque<Move>()

    /**
     * Add a move to queue
     */
    fun move(move: Move) = lazyMovements.push(move)

    /**
     * Make action
     */
    fun act() = lazyMovements.poll() ?: Move.STAY
}
