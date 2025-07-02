package player.common

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PerspectiveCamera
import constants.MapDimensionsConstants.MAP_CELL_SIZE
import player.enums.PlayerStates
import xeengine.src.main.common.constants.global.GlobalDirectionsConstants
import xeengine.src.main.common.globals.Globals
import utils.Position
import xeengine.src.main.XeenTime.XeenTime
import xeengine.src.main.common.constants.global.GlobalCameraConstants.CAMERA_BOBBING_HEIGHT
import xeengine.src.main.common.constants.global.GlobalCameraConstants.CAMERA_BOBBING_TIME
import xeengine.src.main.common.constants.global.GlobalCameraConstants.CAMERA_FAR
import xeengine.src.main.common.constants.global.GlobalCameraConstants.CAMERA_FOW
import xeengine.src.main.common.constants.global.GlobalCameraConstants.CAMERA_HEIGHT
import xeengine.src.main.common.constants.global.GlobalCameraConstants.CAMERA_MOVING_TIME
import xeengine.src.main.common.constants.global.GlobalCameraConstants.CAMERA_NEAR
import xeengine.src.main.common.constants.global.GlobalCameraConstants.CAMERA_STEP_SIZE
import xeengine.src.main.utils.Coordinates
import kotlin.math.sin

class Player private constructor() {
    private val position = Position()
    val facing = GlobalDirectionsConstants.DIRECTION_NORTH.get()
    var camera: PerspectiveCamera
    var busy = false
    var actionProgress = 0f
    private var state = PlayerStates.STANDING
    private var target = Position()
    private val map = Globals.get().map

    init {
        position.x = map.initCoordinates.x * MAP_CELL_SIZE
        position.y = CAMERA_HEIGHT
        position.z = map.initCoordinates.z * MAP_CELL_SIZE
        camera = PerspectiveCamera(CAMERA_FOW, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        camera.position.set(position.x, position.y, position.z)
        camera.lookAt(position.x + facing.x, position.y, position.z + facing.z)
        camera.near = CAMERA_NEAR
        camera.far = CAMERA_FAR
    }

    fun getXZPosition(): Coordinates {
        return Coordinates((position.x / 2).toInt(), (position.z / 2).toInt())
    }

    private fun startMovement(): Boolean {
        if (busy) return false
        busy = true
        actionProgress = 0f
        return true
    }

    fun walkForward() {
        if (!startMovement()) return
        state = PlayerStates.WALKING_FORWARD
        target = Position(
            position.x + facing.x * CAMERA_STEP_SIZE,
            position.y,
            position.z + facing.z * CAMERA_STEP_SIZE
        )
    }

    fun walkBackward() {
        if (!startMovement()) return
        state = PlayerStates.WALKING_BACKWARD
        target = Position(
            position.x - facing.x * CAMERA_STEP_SIZE,
            position.y,
            position.z - facing.z * CAMERA_STEP_SIZE
        )
    }

    fun strafeRight() {
        if (!startMovement()) return
        state = PlayerStates.STRAFING_RIGHT
        target = Position(
            position.x - facing.z * CAMERA_STEP_SIZE,
            position.y,
            position.z + facing.x * CAMERA_STEP_SIZE
        )
    }

    fun strafeLeft() {
        if (!startMovement()) return
        state = PlayerStates.STRAFING_LEFT
        target = Position(
            position.x + facing.z * CAMERA_STEP_SIZE,
            position.y,
            position.z - facing.x * CAMERA_STEP_SIZE
        )
    }

    fun turnRight() {
        if (!startMovement()) return
        state = PlayerStates.TURNING_RIGHT
        target = when(facing) {
            GlobalDirectionsConstants.DIRECTION_NORTH.get() -> Position(GlobalDirectionsConstants.DIRECTION_EAST.get())
            GlobalDirectionsConstants.DIRECTION_EAST.get() -> Position(GlobalDirectionsConstants.DIRECTION_SOUTH.get())
            GlobalDirectionsConstants.DIRECTION_SOUTH.get() -> Position(GlobalDirectionsConstants.DIRECTION_WEST.get())
            else -> Position(GlobalDirectionsConstants.DIRECTION_NORTH.get())
        }
    }

    fun turnLeft() {
        if (!startMovement()) return
        state = PlayerStates.TURNING_RIGHT
        target = when(facing) {
            GlobalDirectionsConstants.DIRECTION_NORTH.get() -> Position(GlobalDirectionsConstants.DIRECTION_WEST.get())
            GlobalDirectionsConstants.DIRECTION_WEST.get() -> Position(GlobalDirectionsConstants.DIRECTION_SOUTH.get())
            GlobalDirectionsConstants.DIRECTION_SOUTH.get() -> Position(GlobalDirectionsConstants.DIRECTION_EAST.get())
            else -> Position(GlobalDirectionsConstants.DIRECTION_NORTH.get())
        }
    }

    fun update() {
        val delta = Gdx.graphics.deltaTime
        var bobbingAmount = 0.0

        //move
        if (state in arrayOf(PlayerStates.WALKING_FORWARD, PlayerStates.WALKING_BACKWARD, PlayerStates.STRAFING_LEFT, PlayerStates.STRAFING_RIGHT)) {
            actionProgress += delta / CAMERA_MOVING_TIME

            if (
                target.x < 0 ||
                target.z < 0 ||
                target.x / MAP_CELL_SIZE > map.height() - 1 ||
                target.z / MAP_CELL_SIZE > map.width() - 1 ||
                !map.getCell((target.x / MAP_CELL_SIZE).toInt(), 0, (target.z / MAP_CELL_SIZE).toInt()).passable) {
                state = PlayerStates.STANDING
                busy = false
                return
            }

            position.set(position.cpy().lerp(target, actionProgress))

            if (actionProgress < 1f) {
                bobbingAmount = sin((actionProgress * CAMERA_BOBBING_TIME * Math.PI.toFloat()).toDouble()) * CAMERA_BOBBING_HEIGHT
            } else {
                position.set(target)
                state = PlayerStates.STANDING
                busy = false
                XeenTime.time += 1
            }
        }

        //turn
        else if (state in arrayOf(PlayerStates.TURNING_LEFT, PlayerStates.TURNING_RIGHT)) {
            actionProgress += delta / CAMERA_MOVING_TIME
            facing.set(facing.cpy().lerp(target, actionProgress))

            if (actionProgress >= 1f) {
                facing.set(target)
                state = PlayerStates.STANDING
                busy = false
            }
        }

        camera.position.set(position.x, (position.y + bobbingAmount).toFloat(), position.z)
        camera.lookAt(position.x + facing.x, (position.y + bobbingAmount).toFloat(), position.z + facing.z)
    }

    companion object {
        @Volatile private var instance: Player? = null
        fun get(): Player =
            instance ?: synchronized(this) {
                instance ?: Player().also { instance = it }
            }
    }
}