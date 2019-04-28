package com.rogue.actor

import com.rogue.map.LevelMap
import com.rogue.utils.Move


abstract class Actor(val health: Health, val damage: Int, val face: Char) {
    data class Health(val isDestroyable: Boolean, var hp: Int) {
        val isDead: Boolean
            get() = hp <= 0
    }

    abstract fun act(levelMap: LevelMap): Move
}
