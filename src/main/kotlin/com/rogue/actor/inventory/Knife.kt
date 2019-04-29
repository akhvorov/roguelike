package com.rogue.actor.inventory

import kotlinx.serialization.Serializable
import java.util.*
import kotlin.random.Random

@Serializable
data class Knife(val damage: Int, val id: String = UUID.randomUUID().toString()) {
    companion object {
        fun random() = Knife(Random.nextInt(1, 5))
    }
}
