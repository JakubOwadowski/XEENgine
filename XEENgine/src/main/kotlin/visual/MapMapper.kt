package visual

import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import visual.models.FloorModel
import visual.models.HorizontalWallModel
import visual.models.VerticalWallModel
import xeengine.src.main.visual.textures.Textures
import java.io.File

fun mapMap(name: String, paths: Array<String>, width: Int, height: Int, levels: Int, skybox: String): Map {

    val map = Map(name, width, height, levels, skybox)
    val gsonParser = Gson()
    val type = object : TypeToken<kotlin.collections.Map<String, Any>>() {}
    val y = 0

    paths.forEach { path ->
        val json = gsonParser.fromJson(File(path).readText(), type)
        json.keys.forEach { key ->
            val keySplit = key.split(", ")
            val z = keySplit[0].toInt()
            val x = keySplit[1].toInt()
            val cell = Cell()
            val value = json[key] as kotlin.collections.Map<*, *>

            val roof = value["roof"] as kotlin.collections.Map<*, *>
            if (roof["present"] == true) {
                cell.hasRoof = true
                cell.hasRoof = true
            }

            val floor = value["floor"] as kotlin.collections.Map<*, *>
            if (floor["present"] == true) {
                val texture = Textures.get(floor["texture"].toString())
                cell.floorInstance = ModelInstance(FloorModel(texture).model)
            }

            val passable = value["passable"]
            if (passable == false) {
                cell.passable = false
            }

            val walls = value["walls"] as kotlin.collections.Map<*, *>

            val northWall = walls["north"] as kotlin.collections.Map<*, *>
            if (northWall["present"] == true) {
                cell.walls[0].isPresent = true
                val texture = Textures.get(northWall["texture"].toString())
                cell.walls[0].model = ModelInstance(HorizontalWallModel(texture).model)
            }

            val westWall = walls["west"] as kotlin.collections.Map<*, *>
            if (westWall["present"] == true) {
                cell.walls[1].isPresent = true
                val texture = Textures.get(westWall["texture"].toString())
                cell.walls[1].model = ModelInstance(VerticalWallModel(texture).model)
            }

            val eastWall = walls["east"] as kotlin.collections.Map<*, *>
            if (eastWall["present"] == true) {
                cell.walls[2].isPresent = true
                val texture = Textures.get(eastWall["texture"].toString())
                cell.walls[2].model = ModelInstance(VerticalWallModel(texture).model)
            }

            val southWall = walls["south"] as kotlin.collections.Map<*, *>
            if (southWall["present"] == true) {
                cell.walls[3].isPresent = true
                val texture = Textures.get(southWall["texture"].toString())
                cell.walls[3].model = ModelInstance(HorizontalWallModel(texture).model)
            }

            map.setCell(x, y, z, cell)
        }
    }



    return map
}