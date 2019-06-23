package com.rogue.draw

import com.rogue.GameConfig
import org.hexworks.zircon.api.*

object Application {
    val ui by lazy {
        SwingApplications.startTileGrid(
                AppConfigs.newConfig()
                        .withSize(Sizes.create(GameConfig.screenSizeX, GameConfig.screenSizeY))
                        .withTitle("XVOR")
                        .build())
    }
}
