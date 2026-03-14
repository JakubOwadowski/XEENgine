package xeengine.visual.primitives

import xeengine.visual.models.FloorModel
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3
import xeengine.visual.models.RoofModel
import xeengine.common.constants.global.GlobalCameraConstants.CAMERA_FLOOR_LEVEL
import xeengine.common.constants.map.MapDimensionsConstants.MAP_CELL_SIZE
import xeengine.common.constants.map.MapDimensionsConstants.MAP_WALL_HEIGHT
import xeengine.player.common.Player
import xeengine.visual.primitives.common.TypedInstance
import xeengine.visual.primitives.enums.InstanceTypeEnum
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
    var ambientObjects: List<AmbientObject> = ArrayList()
    var map3dObject: Map3dObject = Map3dObject(
        Map3dWall(), //North
        Map3dWall(), //West
        Map3dWall(), //East
        Map3dWall()  //South
    )

    fun draw(x: Int, y: Int, z: Int, instances: MutableList<TypedInstance>, player: Player) {
        val xCoordinate = x.toFloat() * MAP_CELL_SIZE
        val zCoordinate = z.toFloat() * MAP_CELL_SIZE
        val yCoordinate = y.toFloat() * MAP_WALL_HEIGHT

        if (hasFloor) {
            floorInstance.transform.setToTranslation(
                xCoordinate,
                CAMERA_FLOOR_LEVEL + yCoordinate,
                zCoordinate
            )
            instances.add(TypedInstance(floorInstance, InstanceTypeEnum.FLOOR, xCoordinate, zCoordinate))
        }

        if (hasRoof) {
            roofInstance.transform.setToTranslation(
                xCoordinate,
                MAP_WALL_HEIGHT + yCoordinate,
                zCoordinate
            )
            instances.add(TypedInstance(roofInstance, InstanceTypeEnum.ROOF, xCoordinate, zCoordinate))
        }

        if (walls.north.present) {
            val northRoofInstance = walls.north.model
            northRoofInstance.transform.setToTranslation(
                xCoordinate,
                MAP_WALL_HEIGHT / 2 + yCoordinate,
                zCoordinate + MAP_CELL_SIZE / 2
            )
            instances.add(TypedInstance(northRoofInstance, InstanceTypeEnum.NORTH_WALL, xCoordinate, zCoordinate))
        }

        if (walls.west.present) {
            val westRoofInstance = walls.west.model
            westRoofInstance.transform.setToTranslation(
                xCoordinate + MAP_CELL_SIZE / 2,
                MAP_WALL_HEIGHT / 2 + yCoordinate,
                zCoordinate
            )
            instances.add(TypedInstance(westRoofInstance, InstanceTypeEnum.WEST_WALL, xCoordinate, zCoordinate))
        }

        if (walls.east.present) {
            val eastRoofInstance = walls.east.model
            eastRoofInstance.transform.setToTranslation(
                xCoordinate - MAP_CELL_SIZE / 2,
                MAP_WALL_HEIGHT / 2 + yCoordinate,
                zCoordinate
            )
            instances.add(TypedInstance(eastRoofInstance, InstanceTypeEnum.EAST_WALL, xCoordinate, zCoordinate))
        }

        if (walls.south.present) {
            val southRoofInstance = walls.south.model
            southRoofInstance.transform.setToTranslation(
                xCoordinate,
                MAP_WALL_HEIGHT / 2 + yCoordinate,
                zCoordinate - MAP_CELL_SIZE / 2
            )
            instances.add(TypedInstance(southRoofInstance, InstanceTypeEnum.SOUTH_WALL, xCoordinate, zCoordinate))
        }


        val up = Vector3.Y
        for (ambientObject in ambientObjects) {
            val ambientObjectInstance = ambientObject.model
            val pos = Vector3(xCoordinate + ambientObject.x, yCoordinate, zCoordinate + ambientObject.z)

            val dir = Vector3(player.camera.position.x, pos.y, player.camera.position.z).sub(pos).nor()
            val tmp = Matrix4().setToLookAt(dir, up)
            tmp.setTranslation(pos)
            ambientObjectInstance.transform.set(tmp)

            val ambientInstance = TypedInstance(ambientObjectInstance, InstanceTypeEnum.AMBIENT, xCoordinate + ambientObject.x, zCoordinate + ambientObject.z)

            instances.add(ambientInstance)
        }
    }
}