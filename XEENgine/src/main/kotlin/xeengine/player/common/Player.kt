package xeengine.player.common

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PerspectiveCamera
import xeengine.common.constants.map.MapDimensionsConstants.MAP_CELL_SIZE
import xeengine.player.enums.PlayerStates.STRAFING_LEFT
import xeengine.player.enums.PlayerStates.TURNING_LEFT
import xeengine.player.enums.PlayerStates.STANDING
import xeengine.player.enums.PlayerStates.WALKING_FORWARD
import xeengine.player.enums.PlayerStates.WALKING_BACKWARD
import xeengine.player.enums.PlayerStates.STRAFING_RIGHT
import xeengine.player.enums.PlayerStates.TURNING_RIGHT
import xeengine.common.globals.Globals
import xeengine.utils.Position
import xeengine.time.XeenTime
import xeengine.common.constants.global.GlobalCameraConstants.CAMERA_BOBBING_HEIGHT
import xeengine.common.constants.global.GlobalCameraConstants.CAMERA_BOBBING_TIME
import xeengine.common.constants.global.GlobalCameraConstants.CAMERA_FAR
import xeengine.common.constants.global.GlobalCameraConstants.CAMERA_FOW
import xeengine.common.constants.global.GlobalCameraConstants.CAMERA_HEIGHT
import xeengine.common.constants.global.GlobalCameraConstants.CAMERA_MOVING_TIME
import xeengine.common.constants.global.GlobalCameraConstants.CAMERA_NEAR
import xeengine.common.constants.global.GlobalCameraConstants.CAMERA_STEP_SIZE
import xeengine.common.constants.global.GlobalDirectionsConstants.DIRECTION_EAST
import xeengine.common.constants.global.GlobalDirectionsConstants.DIRECTION_NORTH
import xeengine.common.constants.global.GlobalDirectionsConstants.DIRECTION_SOUTH
import xeengine.common.constants.global.GlobalDirectionsConstants.DIRECTION_WEST
import xeengine.utils.Coordinates
import xeengine.visual.primitives.Cell
import kotlin.math.sin

class Player private constructor() {
    private val position = Position()
    val facing = DIRECTION_NORTH.get()
    var camera: PerspectiveCamera
    var busy = false
    var actionProgress = 0f
    private var state = STANDING
    private var target = Position()
    private val map = Globals.get().map

    init {
        position.x = map.initCoordinates.x * MAP_CELL_SIZE
        position.y = CAMERA_HEIGHT
        position.z = map.initCoordinates.z * MAP_CELL_SIZE

        camera = PerspectiveCamera(
            CAMERA_FOW,
            Gdx.graphics.width.toFloat(),
            Gdx.graphics.height.toFloat()
        )

        camera.position.set(
            position.x,
            position.y,
            position.z
        )

        camera.lookAt(
            position.x + facing.x,
            position.y,
            position.z + facing.z
        )

        camera.near = CAMERA_NEAR
        camera.far = CAMERA_FAR
    }

    fun getXZPosition(): Coordinates {
        return Coordinates(
            (position.x / 2).toInt(),
            (position.z / 2).toInt()
        )
    }

    fun walkForward() {
        if (busy) return
        busy = true
        actionProgress = 0f
        state = WALKING_FORWARD
        target = Position(
            position.x + facing.x * CAMERA_STEP_SIZE,
            position.y,
            position.z + facing.z * CAMERA_STEP_SIZE
        )
    }

    fun walkBackward() {
        if (busy) return
        busy = true
        actionProgress = 0f
        state = WALKING_BACKWARD
        target = Position(
            position.x - facing.x * CAMERA_STEP_SIZE,
            position.y,
            position.z - facing.z * CAMERA_STEP_SIZE
        )
    }

    fun moveRight() {
        if (busy) return
        busy = true
        actionProgress = 0f
        state = STRAFING_RIGHT
        target = Position(
            position.x - facing.z * CAMERA_STEP_SIZE,
            position.y,
            position.z + facing.x * CAMERA_STEP_SIZE
        )
    }

    fun moveLeft() {
        if (busy) return
        busy = true
        actionProgress = 0f
        state = STRAFING_LEFT
        target = Position(
            position.x + facing.z * CAMERA_STEP_SIZE,
            position.y,
            position.z - facing.x * CAMERA_STEP_SIZE
        )
    }

    fun rotateLeft() {
        if (busy) return
        busy = true
        actionProgress = 0f
        state = TURNING_RIGHT
        target = when(facing) {
            DIRECTION_NORTH.get() -> Position(DIRECTION_EAST.get())
            DIRECTION_EAST.get() -> Position(DIRECTION_SOUTH.get())
            DIRECTION_SOUTH.get() -> Position(DIRECTION_WEST.get())
            else -> Position(DIRECTION_NORTH.get())
        }
    }

    fun rotateRight() {
        if (busy) return
        busy = true
        actionProgress = 0f
        state = TURNING_RIGHT
        target = when(facing) {
            DIRECTION_NORTH.get() -> Position(DIRECTION_WEST.get())
            DIRECTION_WEST.get() -> Position(DIRECTION_SOUTH.get())
            DIRECTION_SOUTH.get() -> Position(DIRECTION_EAST.get())
            else -> Position(DIRECTION_NORTH.get())
        }
    }

    fun update() {
        val delta = Gdx.graphics.deltaTime
        var bobbingAmount = 0.0

        //move
        if (state in arrayOf(WALKING_FORWARD, WALKING_BACKWARD, STRAFING_LEFT, STRAFING_RIGHT)) {
            actionProgress += delta / CAMERA_MOVING_TIME

            //prohibit moving out of bonds
            if (
                target.x < 0 ||
                target.z < 0 ||
                target.x / MAP_CELL_SIZE > map.height() - 1 ||
                target.z / MAP_CELL_SIZE > map.width() - 1
            ) {
                state = STANDING
                busy = false
                return
            }

            val xzPosition: Coordinates = getXZPosition()
            val thisCell: Cell = map.getCell(xzPosition.x, 0, xzPosition.z)
            val nextCell: Cell = map.getCell((target.x / MAP_CELL_SIZE).toInt(), 0, (target.z / MAP_CELL_SIZE).toInt())

            //prohibit moving through blocked paths
            if (
                (target.z > position.z && (!thisCell.walls.north.passable || !nextCell.walls.north.passable)) ||
                (target.z < position.z && (!thisCell.walls.south.passable || !nextCell.walls.south.passable)) ||
                (target.x > position.x && (!thisCell.walls.west.passable || !nextCell.walls.west.passable)) ||
                (target.x < position.x && (!thisCell.walls.east.passable || !nextCell.walls.east.passable))
            ) {
                state = STANDING
                busy = false
                return
            }

            //set new position
            position.set(position.cpy().lerp(target, actionProgress))

            if (actionProgress < 1f) {
                //mid move
                bobbingAmount =
                    sin((actionProgress * CAMERA_BOBBING_TIME * Math.PI.toFloat()).toDouble()) * CAMERA_BOBBING_HEIGHT
            } else {
                //end move
                position.set(target)
                state = STANDING
                busy = false
                XeenTime.time += 1
            }
        }

        //turn
        else if (state in arrayOf(TURNING_LEFT, TURNING_RIGHT)) {
            actionProgress += delta / CAMERA_MOVING_TIME
            facing.set(facing.cpy().lerp(target, actionProgress))

            if (actionProgress >= 1f) {
                facing.set(target)
                state = STANDING
                busy = false
            }
        }

        //update camera
        camera.position.set(
            position.x,
            (position.y + bobbingAmount).toFloat(),
            position.z)

        camera.lookAt(
            position.x + facing.x,
            (position.y + bobbingAmount).toFloat(),
            position.z + facing.z)
    }

    companion object {
        @Volatile private var instance: Player? = null
        fun get(): Player =
            instance ?: synchronized(this) {
                instance ?: Player().also { instance = it }
            }
    }
}