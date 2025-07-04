package player.common

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PerspectiveCamera
import constants.MapDimensionsConstants.MAP_CELL_SIZE
import player.enums.PlayerStates.STRAFING_LEFT
import player.enums.PlayerStates.TURNING_LEFT
import player.enums.PlayerStates.STANDING
import player.enums.PlayerStates.WALKING_FORWARD
import player.enums.PlayerStates.WALKING_BACKWARD
import player.enums.PlayerStates.STRAFING_RIGHT
import player.enums.PlayerStates.TURNING_RIGHT
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
import xeengine.src.main.common.constants.global.GlobalDirectionsConstants.DIRECTION_EAST
import xeengine.src.main.common.constants.global.GlobalDirectionsConstants.DIRECTION_NORTH
import xeengine.src.main.common.constants.global.GlobalDirectionsConstants.DIRECTION_SOUTH
import xeengine.src.main.common.constants.global.GlobalDirectionsConstants.DIRECTION_WEST
import xeengine.src.main.utils.Coordinates
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

    fun strafeRight() {
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

    fun strafeLeft() {
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

    fun turnRight() {
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

    fun turnLeft() {
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

            if (
                target.x < 0 ||
                target.z < 0 ||
                target.x / MAP_CELL_SIZE > map.height() - 1 ||
                target.z / MAP_CELL_SIZE > map.width() - 1 ||
                !map.getCell((target.x / MAP_CELL_SIZE).toInt(), 0, (target.z / MAP_CELL_SIZE).toInt()).passable) {
                state = STANDING
                busy = false
                return
            }

            position.set(position.cpy().lerp(target, actionProgress))

            if (actionProgress < 1f) {
                bobbingAmount =
                    sin((actionProgress * CAMERA_BOBBING_TIME * Math.PI.toFloat()).toDouble()) * CAMERA_BOBBING_HEIGHT
            } else {
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