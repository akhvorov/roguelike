package com.rogue.draw

import com.rogue.Game
import com.rogue.actor.Actor
import com.rogue.actor.PlayerActor
import com.rogue.state.StateService
import com.rogue.utils.Move
import com.rogue.utils.Point
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.extensions.onKeyboardEvent
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventType

object GameScreen {
    data class ActorPresentation(val layer: Layer, val tile: CharacterTile)

    private var initialized = false

    private val actorsOnScene = HashMap<Actor, ActorPresentation>()

    fun init() {
        if (initialized) return

        Application.gameScreen.onKeyboardEvent(KeyboardEventType.KEY_PRESSED) { event, _ ->
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
                KeyCode.ESCAPE -> {
                    StateService.save()
                    Game.isMenu = true
                    UIEventResponses.processed()
                }
                else -> UIEventResponses.pass()
            }
        }

        initialized = true
    }

    fun display() = Application.gameScreen.display()

    fun clear() {
        for (actor in actorsOnScene.keys.toSet()) {
            removeActor(actor)
        }
        actorsOnScene.clear()
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
        Application.gameScreen.removeLayer(presentation.layer)
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

        Application.gameScreen.pushLayer(layer)

        actorsOnScene[actor] = ActorPresentation(layer, actorTile)

        return actorsOnScene[actor]!!
    }
}
