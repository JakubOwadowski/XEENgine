package visual

import visual.models.FloorModel
import com.badlogic.gdx.graphics.g3d.ModelInstance
import constants.CommonConstants
import visual.models.RoofModel
import xeengine.src.main.visual.textures.Textures

class Cell constructor() {
    var walls = arrayOf<Wall>(
        Wall(), //North
        Wall(), //West
        Wall(), //East
        Wall()  //South
    )
    var hasRoof: Boolean = false
    var hasFloor: Boolean = true
    var passable = true
    var floorInstance = ModelInstance(FloorModel(Textures.get("TEXTURE_FLOOR")).model)
    var roofInstance = ModelInstance(RoofModel().model)



    fun draw(x: Int, y: Int, z: Int, instances: MutableList<ModelInstance>) {
        val xCoordinate = x.toFloat() * CommonConstants.CELL_SIZE
        val zCoordinate = z.toFloat() * CommonConstants.CELL_SIZE
        val yCoordinate = y.toFloat() * CommonConstants.WALL_HEIGHT

        if (hasFloor) {
            floorInstance.transform.setToTranslation(
                xCoordinate,
                CommonConstants.FLOOR_LEVEL + yCoordinate,
                zCoordinate)
            instances.add(floorInstance)
        }

        if (hasRoof) {
            roofInstance.transform.setToTranslation(
                xCoordinate,
                CommonConstants.WALL_HEIGHT + yCoordinate,
                zCoordinate)
            instances.add(roofInstance)
        }

        if (walls[0].isPresent) {
            val northRoofInstance = walls[0].model
            northRoofInstance.transform.setToTranslation(
                xCoordinate,
                CommonConstants.WALL_HEIGHT / 2 + yCoordinate,
                zCoordinate + CommonConstants.CELL_SIZE / 2)
            instances.add(northRoofInstance)
        }

        if (walls[1].isPresent) {
            val westRoofInstance = walls[1].model
            westRoofInstance.transform.setToTranslation(
                xCoordinate + CommonConstants.CELL_SIZE / 2,
                CommonConstants.WALL_HEIGHT / 2 + yCoordinate,
                zCoordinate)
            instances.add(westRoofInstance)
        }

        if (walls[2].isPresent) {
            val eastRoofInstance = walls[2].model
            eastRoofInstance.transform.setToTranslation(
                xCoordinate - CommonConstants.CELL_SIZE / 2,
                CommonConstants.WALL_HEIGHT / 2 + yCoordinate,
                zCoordinate)
            instances.add(eastRoofInstance)
        }

        if (walls[3].isPresent) {
            val southRoofInstance = walls[3].model
            southRoofInstance.transform.setToTranslation(
                xCoordinate,
                CommonConstants.WALL_HEIGHT / 2 + yCoordinate,
                zCoordinate - CommonConstants.CELL_SIZE / 2)
            instances.add(southRoofInstance)
        }
    }
}