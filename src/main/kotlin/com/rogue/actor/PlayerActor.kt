package com.rogue.actor

import com.rogue.utils.Move
import java.util.*

/**
 * Player actor
 */
object PlayerActor {
    private val adjureDuration = 3
    private var adjureTime = 0

    val default by lazy { Actor(Actor.Type.Player, Actor.Health(true, 100), 2, '@') }

    private val lazyMovements = ArrayDeque<Move>()

    /**
     * Add a move to queue
     */
    fun move(move: Move) = lazyMovements.push(move)

    /**
     * Make action
     */
    fun act(): Move {
        val realMove = lazyMovements.poll() ?: Move.STAY
        if (adjureTime > 0) {
            adjureTime--
            return realMove.nearest().random()
        }
        return realMove
    }

    fun adjure() {
        adjureTime = adjureDuration
    }
}
