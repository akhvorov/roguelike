package com.rogue.draw

import com.rogue.Game
import com.rogue.GameConfig
import com.rogue.actor.Actor
import com.rogue.actor.PlayerActor
import com.rogue.actor.inventory.Armor
import com.rogue.actor.inventory.Knife
import com.rogue.map.LevelMap
import com.rogue.state.StateService
import com.rogue.utils.Move
import com.rogue.utils.Point
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.extensions.onKeyboardEvent
import org.hexworks.zircon.api.extensions.onSelection
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventType

object GameScreen {
    data class ActorPresentation(val layer: Layer, val tile: CharacterTile)

    private var initialized = false

    private val actorsOnScene = HashMap<Actor, ActorPresentation>()

    object Map {
        val panel = Components.panel()
                .withTitle("Game")
                .withBoxType(BoxType.SINGLE)
                .wrapWithBox(true)
                .withSize(Application.ui.size.withRelativeWidth(-GameConfig.heroPanelWidth))
                .build()
    }

    object Hero {
        val panel = Components.panel()
                .withTitle("Hero")
                .withPosition(Map.panel.size.width, 0)
                .withBoxType(BoxType.SINGLE)
                .wrapWithBox(true)
                .withSize(Application.ui.size.withWidth(GameConfig.heroPanelWidth))
                .build()

        private val healthLabel = Components.label()
                .withText("Health")
                .withPosition(1, 1)
                .build()

        val healthValue = Components.label()
                .withText(GameConfig.startHeroHp.toString())
                .withPosition(1, 3)
                .build()

        private val damageLabel = Components.label()
                .withText("Damage")
                .withPosition(1, 5)
                .build()

        val damageValue = Components.label()
                .withText(GameConfig.startHeroDamage.toString())
                .withPosition(1, 7)
                .build()

        private val levelLabel = Components.label()
                .withText("Level")
                .withPosition(1, 9)
                .build()

        val levelValue = Components.label()
                .withText("0")
                .withPosition(1, 11)
                .build()


        private val knivesLabel = Components.label()
                .withText("Knives")
                .withPosition(1, 13)
                .build()
        val allKnives = HashSet<Knife>()
        val knivesPanel = Components.radioButtonGroup()
                .withPosition(1, 15)
                .withSize(GameConfig.heroPanelWidth - 4, 10)
                .build()

        private val armorsLabel = Components.label()
                .withText("Armors")
                .withPosition(1, 27)
                .build()
        val allArmors = HashSet<Armor>()
        val armorsPanel = Components.radioButtonGroup()
                .withPosition(1, 29)
                .withSize(GameConfig.heroPanelWidth - 4, 10)
                .build()

        init {
            panel.addComponent(healthLabel)
            panel.addComponent(healthValue)

            panel.addComponent(damageLabel)
            panel.addComponent(damageValue)

            panel.addComponent(levelLabel)
            panel.addComponent(levelValue)

            panel.addComponent(knivesLabel)
            panel.addComponent(knivesPanel)
            knivesPanel.onSelection { selection ->
                val inventory = LevelMap.current.getPlayerCell().actor.inventory
                inventory.equippedKnife = inventory.knives.find { it.id == selection.key }!!
            }

            panel.addComponent(armorsLabel)
            panel.addComponent(armorsPanel)
            armorsPanel.onSelection { selection ->
                val inventory = LevelMap.current.getPlayerCell().actor.inventory
                inventory.equippedArmor = inventory.armors.find { it.id == selection.key }!!
            }
        }
    }

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

        Application.gameScreen.addComponent(Map.panel)
        Application.gameScreen.addComponent(Hero.panel)

        initialized = true
    }

    fun display() = Application.gameScreen.display()

    fun updateHeroPanel() {
        val hero = LevelMap.current.getPlayerCell().actor
        Hero.healthValue.text = hero.hp.toString()
        Hero.damageValue.text = hero.damage.toString()
        Hero.levelValue.text = hero.stats.level.toString()

        for (knife in hero.inventory.knives) {
            if (knife !in Hero.allKnives) {
                Hero.allKnives += knife
                Hero.knivesPanel.addOption(knife.id, "Knife +${knife.damage}")
            }
        }

        for (armor in hero.inventory.armors) {
            if (armor !in Hero.allArmors) {
                Hero.allArmors += armor
                Hero.armorsPanel.addOption(armor.id, "Armor +${armor.hp}")
            }
        }
    }

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
                .withOffset(Positions.create(1 + point.x, 1 + point.y))
                .build()
                .fill(actorTile)

        Application.gameScreen.pushLayer(layer)

        actorsOnScene[actor] = ActorPresentation(layer, actorTile)

        return actorsOnScene[actor]!!
    }
}
