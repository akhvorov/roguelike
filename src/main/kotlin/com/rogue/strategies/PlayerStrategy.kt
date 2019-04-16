package com.rogue.strategies

import com.rogue.Actor

object PlayerStrategy : (Actor, List<Actor>) -> Boolean {
    private var health: Int = 5

    override fun invoke(p1: Actor, p2: List<Actor>): Boolean {
        return health > 0
    }
}
