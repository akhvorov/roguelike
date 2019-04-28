package com.rogue.draw

import com.rogue.GameConfig
import org.hexworks.zircon.api.*

object Application {
    val ui by lazy {
        SwingApplications.startTileGrid(
                AppConfigs.newConfig()
                        .withSize(Sizes.create(GameConfig.sizeX, GameConfig.sizeY))
                        .withTitle("XVOR")
                        .build())
    }

    val menuScreen by lazy { Screens.createScreenFor(ui) }
    val gameScreen by lazy { Screens.createScreenFor(ui) }
}
