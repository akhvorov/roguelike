package com.rogue.state

import com.rogue.draw.GameScreen
import com.rogue.map.LevelMap
import com.rogue.map.MapGenerator
import com.rogue.utils.Json
import kotlinx.serialization.KSerializer
import java.io.File

/**
 * State management service
 */
object StateService {
    private val mapStorage = File("map.json")

    /**
     * Reload the map
     */
    fun recreateMap() {
        mapStorage.delete()
        loadOrCreateMap()
    }

    /**
     * Load or create a new map
     */
    fun loadOrCreateMap() {
        GameScreen.clear()
        if (!mapStorage.exists()) {
            mapStorage.createNewFile()
            LevelMap.current = MapGenerator.generateInitialMap()
            save()
        } else {
            LevelMap.current = Json.readValue(LevelMap.serializer(), mapStorage.readText())
        }
        LevelMap.current.reinitScreen()
    }

    /**
     * Save level
     */
    fun save() {
        if (!mapStorage.exists()) {
            mapStorage.createNewFile()
        }

        mapStorage.writeText(Json.writeValueAsString(LevelMap.serializer() as KSerializer<Any>, LevelMap.current))
    }
}
