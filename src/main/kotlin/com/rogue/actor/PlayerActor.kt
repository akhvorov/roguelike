package com.rogue.actor

import com.rogue.utils.Move
import java.util.*

object PlayerActor : Actor(100, 1, '@') {
    private val lazyMovements = ArrayDeque<Move>()

    fun addMovement(move: Move) = lazyMovements.push(move)

    override fun act() = lazyMovements.poll() ?: Move.STAY
}
