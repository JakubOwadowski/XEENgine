package xeengine.visual.primitives

import xeengine.visual.models.FloorModel
import com.badlogic.gdx.graphics.g3d.ModelInstance
import xeengine.visual.models.RoofModel
import xeengine.common.constants.global.GlobalCameraConstants.CAMERA_FLOOR_LEVEL
import xeengine.common.constants.map.MapDimensionsConstants.MAP_CELL_SIZE
import xeengine.common.constants.map.MapDimensionsConstants.MAP_WALL_HEIGHT
import xeengine.visual.textures.Textures

class Cell {
    var walls: Walls = Walls(
        Wall(), //North
        Wall(), //West
        Wall(), //East
        Wall()  //South
    )

    var hasRoof: Boolean = false
    var hasFloor: Boolean = true
    var floorInstance = ModelInstance(FloorModel(Textures.get("TEXTURE_FLOOR")).model)
    var roofInstance = ModelInstance(RoofModel().model)

    fun draw(x: Int, y: Int, z: Int, instances: MutableList<ModelInstance>) {
        val xCoordinate = x.toFloat() * MAP_CELL_SIZE
        val zCoordinate = z.toFloat() * MAP_CELL_SIZE
        val yCoordinate = y.toFloat() * MAP_WALL_HEIGHT

        if (hasFloor) {
            floorInstance.transform.setToTranslation(
                xCoordinate,
                CAMERA_FLOOR_LEVEL + yCoordinate,
                zCoordinate
            )
            instances.add(floorInstance)
        }

        if (hasRoof) {
            roofInstance.transform.setToTranslation(
                xCoordinate,
                MAP_WALL_HEIGHT + yCoordinate,
                zCoordinate
            )
            instances.add(roofInstance)
        }

        if (walls.north.present) {
            val northRoofInstance = walls.north.model
            northRoofInstance.transform.setToTranslation(
                xCoordinate,
                MAP_WALL_HEIGHT / 2 + yCoordinate,
                zCoordinate + MAP_CELL_SIZE / 2
            )
            instances.add(northRoofInstance)
        }

        if (walls.west.present) {
            val westRoofInstance = walls.west.model
            westRoofInstance.transform.setToTranslation(
                xCoordinate + MAP_CELL_SIZE / 2,
                MAP_WALL_HEIGHT / 2 + yCoordinate,
                zCoordinate
            )
            instances.add(westRoofInstance)
        }

        if (walls.east.present) {
            val eastRoofInstance = walls.east.model
            eastRoofInstance.transform.setToTranslation(
                xCoordinate - MAP_CELL_SIZE / 2,
                MAP_WALL_HEIGHT / 2 + yCoordinate,
                zCoordinate
            )
            instances.add(eastRoofInstance)
        }

        if (walls.south.present) {
            val southRoofInstance = walls.south.model
            southRoofInstance.transform.setToTranslation(
                xCoordinate,
                MAP_WALL_HEIGHT / 2 + yCoordinate,
                zCoordinate - MAP_CELL_SIZE / 2
            )
            instances.add(southRoofInstance)
        }
    }
}