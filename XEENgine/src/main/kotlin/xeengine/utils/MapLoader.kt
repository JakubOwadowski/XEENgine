package xeengine.utils

import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import xeengine.visual.models.FloorModel
import xeengine.visual.models.HorizontalWallModel
import xeengine.common.logger.Logger
import xeengine.model.CellDataModel
import xeengine.model.WallDataModel
import xeengine.visual.models.VerticalWallModel
import xeengine.visual.primitives.Cell
import xeengine.visual.primitives.GameMap
import xeengine.visual.primitives.Wall
import xeengine.visual.textures.Textures
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
        ): GameMap {
            Logger.gameLog("Loading map: $name", MapLoader::class)
            val map = GameMap(name, width, height, levels, skybox)
            val gsonParser = Gson()
            val type = object : TypeToken<Map<String, CellDataModel>>() {}
            val y = 0

            paths.forEach { path ->
                val json = gsonParser.fromJson(File(path).readText(), type)
                json.keys.forEach { key ->
                    val keySplit = key.split(", ")
                    val z = keySplit[0].toInt()
                    val x = keySplit[1].toInt()
                    val cell = Cell()
                    val cellData = json[key] as CellDataModel

                    val roofData = cellData.roof
                    if (roofData.present) {
                        cell.hasRoof = true
                    }

                    val floorData = cellData.floor
                    if (floorData.present) {
                        val textureName = floorData.texture.toString()
                        val texture = Textures.get(textureName)
                        cell.floorInstance = ModelInstance(FloorModel(texture).model)
                    }

                    val wallsData = cellData.walls
                    cell.walls.north = mapToWallHorizontal(wallsData.north)
                    cell.walls.west = mapToWallVertical(wallsData.west)
                    cell.walls.east = mapToWallVertical(wallsData.east)
                    cell.walls.south = mapToWallHorizontal(wallsData.south)

                    map.setCell(x, y, z, cell)
                }
            }

            return map
        }

        private fun mapToWallHorizontal(wallData: WallDataModel): Wall {
            var wall = Wall()
            wall.passable = wallData.passable
            if (wallData.present) {
                wall.present = true
                val textureName = wallData.texture.toString()
                val texture = Textures.get(textureName)
                wall.model = ModelInstance(HorizontalWallModel(texture).model)
            }
            return wall
        }

        private fun mapToWallVertical(wallData: WallDataModel): Wall {
            var wall = Wall()
            wall.passable = wallData.passable
            if (wallData.present) {
                wall.present = true
                val textureName = wallData.texture.toString()
                val texture = Textures.get(textureName)
                wall.model = ModelInstance(VerticalWallModel(texture).model)
            }
            return wall
        }
    }
}
