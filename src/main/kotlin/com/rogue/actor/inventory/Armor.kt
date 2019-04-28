package com.rogue.actor.inventory

import kotlinx.serialization.Serializable
import java.util.*
import kotlin.random.Random

@Serializable
data class Armor(val hp: Int, val id: String = UUID.randomUUID().toString()) {
    companion object {
        fun random() = Armor(Random.nextInt(1, 5))
    }
}
