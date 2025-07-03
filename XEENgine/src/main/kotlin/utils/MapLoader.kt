package visual

import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import common.constants.map.MapObjectsConstants.EAST
import common.constants.map.MapObjectsConstants.FLOOR
import common.constants.map.MapObjectsConstants.NORTH
import common.constants.map.MapObjectsConstants.PASSABLE
import common.constants.map.MapObjectsConstants.PRESENT
import common.constants.map.MapObjectsConstants.ROOF
import common.constants.map.MapObjectsConstants.SOUTH
import common.constants.map.MapObjectsConstants.TEXTURE
import common.constants.map.MapObjectsConstants.WALLS
import common.constants.map.MapObjectsConstants.WEST
import visual.models.FloorModel
import visual.models.HorizontalWallModel
import visual.models.VerticalWallModel
import xeengine.src.main.common.logger.Logger
import xeengine.src.main.visual.textures.Textures
import java.io.File


class MapLoader {
    companion object {
        fun loadMap(
            name: String,
            paths: Array<String>,
            width: Int,
            height: Int,
            levels: Int,
            skybox: String
        ): Map {
            Logger.gameLog("Loading map: $name", MapLoader::class)
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

                    val roof = value[ROOF] as kotlin.collections.Map<*, *>
                    if (roof[PRESENT] == true) {
                        cell.hasRoof = true
                        cell.hasRoof = true
                    }

                    val floor = value[FLOOR] as kotlin.collections.Map<*, *>
                    if (floor[PRESENT] == true) {
                        val textureName = floor[TEXTURE].toString()
                        val texture = Textures.get(textureName)
                        cell.floorInstance = ModelInstance(FloorModel(texture).model)
                    }

                    val passable = value[PASSABLE]
                    if (passable == false) {
                        cell.passable = false
                    }

                    val walls = value[WALLS] as kotlin.collections.Map<*, *>

                    val northWall = walls[NORTH] as kotlin.collections.Map<*, *>
                    if (northWall[PRESENT] == true) {
                        cell.walls[0].isPresent = true
                        val textureName = northWall[TEXTURE].toString()
                        val texture = Textures.get(textureName)
                        cell.walls[0].model = ModelInstance(HorizontalWallModel(texture).model)
                    }

                    val westWall = walls[WEST] as kotlin.collections.Map<*, *>
                    if (westWall[PRESENT] == true) {
                        cell.walls[1].isPresent = true
                        val textureName = westWall[TEXTURE].toString()
                        val texture = Textures.get(textureName)
                        cell.walls[1].model = ModelInstance(VerticalWallModel(texture).model)
                    }

                    val eastWall = walls[EAST] as kotlin.collections.Map<*, *>
                    if (eastWall[PRESENT] == true) {
                        cell.walls[2].isPresent = true
                        val textureName = eastWall[TEXTURE].toString()
                        val texture = Textures.get(textureName)
                        cell.walls[2].model = ModelInstance(VerticalWallModel(texture).model)
                    }

                    val southWall = walls[SOUTH] as kotlin.collections.Map<*, *>
                    if (southWall[PRESENT] == true) {
                        cell.walls[3].isPresent = true
                        val textureName = southWall[TEXTURE].toString()
                        val texture = Textures.get(textureName)
                        cell.walls[3].model = ModelInstance(HorizontalWallModel(texture).model)
                    }

                    map.setCell(x, y, z, cell)
                }
            }

            return map
        }
    }
}
