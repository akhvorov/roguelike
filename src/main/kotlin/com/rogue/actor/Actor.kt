package com.rogue.actor

import com.rogue.actor.items.EnemyStrategy
import com.rogue.actor.items.Armor
import com.rogue.actor.items.Knife
import com.rogue.map.LevelMap
import com.rogue.utils.Move
import kotlinx.serialization.Serializable
import java.util.*
import kotlin.collections.HashSet

/**
 * Actor is any object on a map
 */
@Serializable
data class Actor(val type: Type, val health: Health, val defaultDamage: Int, val face: Char, val stats: Stats = Stats(), val inventory: Inventory = Inventory(),
                 val id: String = UUID.randomUUID().toString()) {
    val damage: Int
        get() = defaultDamage + stats.additionalDamage + inventory.equippedKnife.damage

    val hp: Int
        get() = health.defaultHp + inventory.equippedArmor.hp


    val isDead: Boolean
        get() = hp <= 0

    /**
     * Inventory of actor
     */
    @Serializable
    data class Inventory(var equippedKnife: Knife = Knife(0), val knives: HashSet<Knife> = HashSet(),
                         var equippedArmor: Armor = Armor(0), val armors: HashSet<Armor> = HashSet())

    /**
     * Statistics of actor
     */
    @Serializable
    data class Stats(var killed: Int = 0) {
        val level: Int
            get() = killed / 5

        val additionalDamage
            get() = level / 2
    }

    @Serializable
    data class Health(val destroyable: Boolean, var defaultHp: Int)

    //Why enum and not polymorphism?
    //This is the simplest way to make state serializable.
    /**
     * Type of actor
     */
    enum class Type {
        Player,
        Wall,
        BraveEnemy,
        CowardEnemy,
        OtherEnemy,
        KnifeInventory,
        ArmorInventory,
        Conjurer
    }

    /**
     * Act
     *
     * @param levelMap level map, where actor live
     * @return direction to move
     */
    fun act(levelMap: LevelMap): Move = when (type) {
        Type.Player -> PlayerActor.act()
        Type.Wall -> Move.STAY
        Type.BraveEnemy -> EnemyStrategy.Brave.act(this, levelMap)
        Type.CowardEnemy -> EnemyStrategy.Coward.act(this, levelMap)
        Type.Conjurer -> Move.STAY
        Type.OtherEnemy -> EnemyStrategy.NotMyBusiness.act(this, levelMap)
        Type.KnifeInventory -> Move.STAY
        Type.ArmorInventory -> Move.STAY
    }

    /**
     * Interact with another actor
     *
     * @param actor actor to interact
     */
    fun affect(actor: Actor) {
        when (type) {
            Type.KnifeInventory -> actor.inventory.knives += Knife.random()
            Type.ArmorInventory -> actor.inventory.armors += Armor.random()
            Type.Conjurer -> if (actor.type == Type.Player) PlayerActor.adjure()
        }
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
