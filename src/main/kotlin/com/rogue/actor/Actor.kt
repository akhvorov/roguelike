package com.rogue.actor

import com.rogue.actor.enemy.EnemyStrategy
import com.rogue.map.LevelMap
import com.rogue.utils.Move
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class Actor(val type: Type, val health: Health, val damage: Int, val face: Char, val id: String = UUID.randomUUID().toString()) {
    @Serializable
    data class Health(val destroyable: Boolean, var hp: Int) {
        val isDead: Boolean
            get() = hp <= 0
    }

    //Why enum and not polymorphism?
    //This is the simplest way to make state serializable.
    enum class Type {
        Player,
        Wall,
        BraveEnemy,
        CowardEnemy,
        OtherEnemy
    }

    fun act(levelMap: LevelMap): Move = when (type) {
        Type.Player -> PlayerActor.act()
        Type.Wall -> Move.STAY
        Type.BraveEnemy -> EnemyStrategy.Brave.act(this, levelMap)
        Type.CowardEnemy -> EnemyStrategy.Coward.act(this, levelMap)
        Type.OtherEnemy -> EnemyStrategy.NotMyBusiness.act(this, levelMap)
    }

    //Override with UUID usage only
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Actor

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
