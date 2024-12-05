package visual

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import constants.CommonConstants
import visual.models.floorModel
import visual.models.roofModel
import visual.models.horizontalWallModel
import visual.models.verticalWallModel
import kotlin.collections.mutableListOf
import kotlin.random.Random

class Cell {
    var walls = arrayOf<Wall>(
        Wall(), //North
        Wall(), //West
        Wall(), //East
        Wall()  //South
    )
    private var sprites: MutableList<String> = mutableListOf()
    var hasRoof: Boolean = false
    var passable = true

    constructor()
    constructor(hasRoof: Boolean, walls: Array<Boolean>, passable: Boolean) {
        this.hasRoof = hasRoof
        for (i: Int in 0 until walls.size) {
            this.walls[i].isPresent = walls[i]
        }
        this.passable = passable
    }

    fun draw(x: Int, z: Int, instances: MutableList<ModelInstance>) {
        // Render floor
        val floorInstance = ModelInstance(floorModel)
        val randomColor = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), 1f)
        val xCoordinate = x.toFloat() * CommonConstants.CELL_SIZE
        val zCoordinate = z.toFloat() * CommonConstants.CELL_SIZE

        //Render floor
        floorInstance.materials.first().set(ColorAttribute.createDiffuse(randomColor))
        floorInstance.transform.setToTranslation(
            xCoordinate,
            CommonConstants.FLOOR_LEVEL,
            zCoordinate)
        instances.add(floorInstance)

        // Render roof
        if (hasRoof) {
            val roofInstance = ModelInstance(roofModel)
            roofInstance.transform.setToTranslation(
                xCoordinate,
                CommonConstants.WALL_HEIGHT,
                zCoordinate)
            instances.add(roofInstance)
        }

        // Render walls
        if (walls[0].isPresent) {
            val northRoofInstance = ModelInstance(horizontalWallModel)
            northRoofInstance.transform.setToTranslation(
                xCoordinate,
                CommonConstants.WALL_HEIGHT / 2,
                zCoordinate + CommonConstants.CELL_SIZE / 2)
            instances.add(northRoofInstance)
        }

        if (walls[1].isPresent) {
            val westRoofInstance = ModelInstance(verticalWallModel)
            westRoofInstance.transform.setToTranslation(
                xCoordinate + CommonConstants.CELL_SIZE / 2,
                CommonConstants.WALL_HEIGHT / 2,
                zCoordinate)
            instances.add(westRoofInstance)
        }

        if (walls[2].isPresent) {
            val westRoofInstance = ModelInstance(verticalWallModel)
            westRoofInstance.transform.setToTranslation(
                xCoordinate - CommonConstants.CELL_SIZE / 2,
                CommonConstants.WALL_HEIGHT / 2,
                zCoordinate)
            instances.add(westRoofInstance)
        }

        if (walls[3].isPresent) {
            val southRoofInstance = ModelInstance(horizontalWallModel)
            southRoofInstance.transform.setToTranslation(
                xCoordinate,
                CommonConstants.WALL_HEIGHT / 2,
                zCoordinate - CommonConstants.CELL_SIZE / 2)
            instances.add(southRoofInstance)
        }
    }

    fun dispose() {
        roofModel.dispose()
        floorModel.dispose()
        horizontalWallModel.dispose()
    }
}