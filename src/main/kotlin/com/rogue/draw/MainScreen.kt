package com.rogue.draw

import com.rogue.GameConfig
import com.rogue.actor.Actor
import com.rogue.actor.PlayerActor
import com.rogue.utils.Move
import com.rogue.utils.Point
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.extensions.onKeyboardEvent
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventType

object MainScreen {
    data class ActorPresentation(val layer: Layer, val tile: CharacterTile)

    private val actorsOnScene = HashMap<Actor, ActorPresentation>()

    private val application by lazy {
        SwingApplications.startTileGrid(
                AppConfigs.newConfig()
                        .withSize(Sizes.create(GameConfig.sizeX, GameConfig.sizeY))
                        .build())
    }

    fun init() {
        application
    }

    fun registerPlayer(point: Point, actor: PlayerActor): ActorPresentation {
        val presentation = registerActor(point, actor)

        application.onKeyboardEvent(KeyboardEventType.KEY_PRESSED) { event, _ ->
            when (event.code) {
                KeyCode.UP -> {
                    PlayerActor.move(Move.UP)
                    UIEventResponses.processed()
                }
                KeyCode.DOWN -> {
                    PlayerActor.move(Move.DOWN)
                    UIEventResponses.processed()
                }
                KeyCode.LEFT -> {
                    PlayerActor.move(Move.LEFT)
                    UIEventResponses.processed()
                }
                KeyCode.RIGHT -> {
                    PlayerActor.move(Move.RIGHT)
                    UIEventResponses.processed()
                }
                else -> UIEventResponses.pass()
            }
        }
        return presentation
    }

    fun moveActor(actor: Actor, move: Move): Boolean {
        val presentation = actorsOnScene[actor] ?: return false
        when (move) {
            Move.UP -> presentation.layer.moveUpBy(1)
            Move.DOWN -> presentation.layer.moveDownBy(1)
            Move.LEFT -> presentation.layer.moveLeftBy(1)
            Move.RIGHT -> presentation.layer.moveRightBy(1)
        }
        return true
    }

    fun removeActor(actor: Actor): Boolean {
        val presentation = actorsOnScene[actor] ?: return false
        application.removeLayer(presentation.layer)
        actorsOnScene.remove(actor)
        return true
    }

    fun registerActor(point: Point, actor: Actor): ActorPresentation {
        if (actor in actorsOnScene) return actorsOnScene[actor]!!

        val actorTile = Tiles.newBuilder()
                .withBackgroundColor(ANSITileColor.BLACK)
                .withForegroundColor(ANSITileColor.WHITE)
                .withCharacter(actor.face)
                .buildCharacterTile()

        val layer = Layers.newBuilder()
                .withSize(Sizes.one())
                .withOffset(Positions.create(point.x, point.y))
                .build()
                .fill(actorTile)

        application.pushLayer(layer)

        actorsOnScene[actor] = ActorPresentation(layer, actorTile)

        return actorsOnScene[actor]!!
    }
}
