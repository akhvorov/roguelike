package com.rogue.draw

import com.rogue.Game
import com.rogue.state.StateService
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.UIEventResponses
import org.hexworks.zircon.api.extensions.onMouseEvent
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.uievent.MouseEventType

object MenuScreen : Screen {
    val menuScreen by lazy { Screens.createScreenFor(Application.ui) }

    private var initialized: Boolean = false

    override fun init() {
        if (initialized) return

        val menuPanel = Components.panel()
                .withTitle("XVOR: roguelike once more")
                .withBoxType(BoxType.SINGLE)
                .wrapWithBox(true)
                .withSize(Application.ui.size)
                .build()

        val newGame = Components.button()
                .withPosition(1, 1)
                .withText("New game").build()

        newGame.onMouseEvent(MouseEventType.MOUSE_CLICKED) { _, _ ->
            StateService.recreateMap()
            Game.isMenu = false
            UIEventResponses.processed()
        }

        val loadGame = Components.button()
                .withPosition(1, 3)
                .withText("Load game").build()

        loadGame.onMouseEvent(MouseEventType.MOUSE_CLICKED) { _, _ ->
            StateService.loadOrCreateMap()
            Game.isMenu = false
            UIEventResponses.processed()
        }

        menuPanel.addComponent(newGame)
        menuPanel.addComponent(loadGame)


        menuPanel.applyColorTheme(ColorThemeResource.TRON.getTheme())

        menuScreen.addComponent(menuPanel)

        initialized = true
    }

    override fun display() = menuScreen.display()
}
