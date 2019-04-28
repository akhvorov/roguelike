package com.rogue.actor

import com.rogue.utils.Move


abstract class Actor(val health: Int, val damage: Int, val face: Char) {
    abstract fun act(): Move
}
