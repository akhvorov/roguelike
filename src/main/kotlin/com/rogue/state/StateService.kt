package com.rogue.state

import com.rogue.draw.GameScreen
import com.rogue.map.LevelMap
import com.rogue.map.MapGenerator
import com.rogue.utils.Json
import kotlinx.serialization.KSerializer
import java.io.File

object StateService {
    private val mapStorage = File("map.json")

    fun recreateMap() {
        mapStorage.delete()
        loadOrCreateMap()
    }

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

    fun save() {
        if (!mapStorage.exists()) {
            mapStorage.createNewFile()
        }

        mapStorage.writeText(Json.writeValueAsString(LevelMap.serializer() as KSerializer<Any>, LevelMap.current))
    }
}
