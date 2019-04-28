package com.rogue.actor

import com.rogue.map.LevelMap
import com.rogue.utils.Move
import java.util.*

object PlayerActor : Actor(Health(true, 100), 1, '@') {
    private val lazyMovements = ArrayDeque<Move>()

    fun move(move: Move) = lazyMovements.push(move)

    override fun act(levelMap: LevelMap) = lazyMovements.poll() ?: Move.STAY
}
