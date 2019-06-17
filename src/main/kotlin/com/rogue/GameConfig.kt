package com.rogue

/**
 * Game configuration
 */
object GameConfig {
    const val screenSizeX = 61
    const val screenSizeY = 61

    const val borderSize = 1

    const val heroPanelWidth = 20

    const val mapSizeX = screenSizeX - heroPanelWidth - 2 * borderSize
    const val mapSizeY = screenSizeY - 2 * borderSize

    const val startHeroHp = 100
    const val startHeroDamage = 2


    const val knives = 3
    const val armors = 3
    const val cowards = 5
    const val braves = 15
    const val others = 5
}
