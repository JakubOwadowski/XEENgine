package visual

import visual.models.FloorModel
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import constants.CommonConstants
import visual.models.HorizontalWallModel
import visual.models.RoofModel
import visual.models.VerticalWallModel
import xeengine.src.main.visual.models.visual.models.HorizontalTriangleWallModel
import xeengine.src.main.visual.textures.TEXTURE_WALL
import kotlin.random.Random

class Cell {
    var walls = arrayOf<Wall>(
        Wall(), //North
        Wall(), //West
        Wall(), //East
        Wall()  //South
    )
    var hasRoof: Boolean = false
    var hasFloor: Boolean = true
    var passable = true

    constructor()
    constructor(hasRoof: Boolean, hasFloor: Boolean, walls: Array<Wall>, passable: Boolean) {
        this.hasRoof = hasRoof
        this.hasFloor = hasFloor
        for (i: Int in 0 until walls.size) {
            this.walls = walls
        }
        this.passable = passable
    }

    fun draw(x: Int, y: Int, z: Int, instances: MutableList<ModelInstance>) {
        // Render floor
        val floorInstance = ModelInstance(FloorModel().model)
        val randomColor = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), 1f)
        val xCoordinate = x.toFloat() * CommonConstants.CELL_SIZE
        val zCoordinate = z.toFloat() * CommonConstants.CELL_SIZE
        val yCoordinate = y.toFloat() * CommonConstants.WALL_HEIGHT

        if (hasFloor) {
            floorInstance.materials.first().set(ColorAttribute.createDiffuse(randomColor))
            floorInstance.transform.setToTranslation(
                xCoordinate,
                CommonConstants.FLOOR_LEVEL + yCoordinate,
                zCoordinate)
            instances.add(floorInstance)
        }


        // Render roof
        if (hasRoof) {
            val roofInstance = ModelInstance(RoofModel().model)
            roofInstance.transform.setToTranslation(
                xCoordinate,
                CommonConstants.WALL_HEIGHT + yCoordinate,
                zCoordinate)
            instances.add(roofInstance)
        }

        // Render walls
        if (walls[0].isPresent) {
            val northRoofInstance = ModelInstance(HorizontalWallModel(TEXTURE_WALL).model)
            northRoofInstance.transform.setToTranslation(
                xCoordinate,
                CommonConstants.WALL_HEIGHT / 2 + yCoordinate,
                zCoordinate + CommonConstants.CELL_SIZE / 2)
            instances.add(northRoofInstance)
        }

        if (walls[1].isPresent) {
            val westRoofInstance = ModelInstance(VerticalWallModel(TEXTURE_WALL).model)
            westRoofInstance.transform.setToTranslation(
                xCoordinate + CommonConstants.CELL_SIZE / 2,
                CommonConstants.WALL_HEIGHT / 2 + yCoordinate,
                zCoordinate)
            instances.add(westRoofInstance)
        }

        if (walls[2].isPresent) {
            val westRoofInstance = ModelInstance(VerticalWallModel(TEXTURE_WALL).model)
            westRoofInstance.transform.setToTranslation(
                xCoordinate - CommonConstants.CELL_SIZE / 2,
                CommonConstants.WALL_HEIGHT / 2 + yCoordinate,
                zCoordinate)
            instances.add(westRoofInstance)
        }

        if (walls[3].isPresent) {
            val southRoofInstance = ModelInstance(HorizontalWallModel(TEXTURE_WALL).model)
            southRoofInstance.transform.setToTranslation(
                xCoordinate,
                CommonConstants.WALL_HEIGHT / 2 + yCoordinate,
                zCoordinate - CommonConstants.CELL_SIZE / 2)
            instances.add(southRoofInstance)
        }
    }

    fun dispose() {
        for (wall in walls) {

        }
    }
}