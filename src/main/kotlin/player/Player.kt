package player

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PerspectiveCamera
import constants.CommonConstants
import constants.CommonConstants.CAMERA_HEIGHT
import constants.CommonConstants.CELL_SIZE
import constants.DirectionsConstants
import globals.Globals
import utils.Position
import xeengine.src.main.XeenTime.XeenTime
import xeengine.src.main.utils.Coordinates
import kotlin.math.sin

class Player private constructor() {
    private val position = Position()
    val facing = DirectionsConstants.NORTH.get()
    var camera: PerspectiveCamera
    var busy = false
    var actionProgress = 0f
    private var state = PlayerStates.STANDING
    private var target = Position()
    private val map = Globals.get().map

    init {
        position.x = map.initCoordinates.x * CELL_SIZE
        position.y = CAMERA_HEIGHT
        position.z = map.initCoordinates.z * CELL_SIZE
        camera = PerspectiveCamera(CommonConstants.PLAYER_FOW, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        camera.position.set(position.x, position.y, position.z)
        camera.lookAt(position.x + facing.x, position.y, position.z + facing.z)
        camera.near = CommonConstants.CAMERA_NEAR
        camera.far = CommonConstants.CAMERA_FAR
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
            position.x + facing.x * CommonConstants.PLAYER_STEP_SIZE,
            position.y,
            position.z + facing.z * CommonConstants.PLAYER_STEP_SIZE
        )
    }

    fun walkBackward() {
        if (!startMovement()) return
        state = PlayerStates.WALKING_BACKWARD
        target = Position(
            position.x - facing.x * CommonConstants.PLAYER_STEP_SIZE,
            position.y,
            position.z - facing.z * CommonConstants.PLAYER_STEP_SIZE
        )
    }

    fun strafeRight() {
        if (!startMovement()) return
        state = PlayerStates.STRAFING_RIGHT
        target = Position(
            position.x - facing.z * CommonConstants.PLAYER_STEP_SIZE,
            position.y,
            position.z + facing.x * CommonConstants.PLAYER_STEP_SIZE
        )
    }

    fun strafeLeft() {
        if (!startMovement()) return
        state = PlayerStates.STRAFING_LEFT
        target = Position(
            position.x + facing.z * CommonConstants.PLAYER_STEP_SIZE,
            position.y,
            position.z - facing.x * CommonConstants.PLAYER_STEP_SIZE
        )
    }

    fun turnRight() {
        if (!startMovement()) return
        state = PlayerStates.TURNING_RIGHT
        target = when(facing) {
            DirectionsConstants.NORTH.get() -> Position(DirectionsConstants.EAST.get())
            DirectionsConstants.EAST.get() -> Position(DirectionsConstants.SOUTH.get())
            DirectionsConstants.SOUTH.get() -> Position(DirectionsConstants.WEST.get())
            else -> Position(DirectionsConstants.NORTH.get())
        }
    }

    fun turnLeft() {
        if (!startMovement()) return
        state = PlayerStates.TURNING_RIGHT
        target = when(facing) {
            DirectionsConstants.NORTH.get() -> Position(DirectionsConstants.WEST.get())
            DirectionsConstants.WEST.get() -> Position(DirectionsConstants.SOUTH.get())
            DirectionsConstants.SOUTH.get() -> Position(DirectionsConstants.EAST.get())
            else -> Position(DirectionsConstants.NORTH.get())
        }
    }

    fun update() {
        val delta = Gdx.graphics.deltaTime
        var bobbingAmount = 0.0

        //move
        if (state in arrayOf(PlayerStates.WALKING_FORWARD, PlayerStates.WALKING_BACKWARD, PlayerStates.STRAFING_LEFT, PlayerStates.STRAFING_RIGHT)) {
            actionProgress += delta / CommonConstants.PLAYER_SPEED

            if (
                target.x < 0 ||
                target.z < 0 ||
                target.x / CELL_SIZE > map.height() - 1 ||
                target.z / CELL_SIZE > map.width() - 1 ||
                !map.getCell((target.x / CELL_SIZE).toInt(), 0, (target.z / CELL_SIZE).toInt()).passable) {
                state = PlayerStates.STANDING
                busy = false
                return
            }

            position.set(position.cpy().lerp(target, actionProgress))

            if (actionProgress < 1f) {
                bobbingAmount = sin((actionProgress * CommonConstants.BOBBING_SPEED * Math.PI.toFloat()).toDouble()) * CommonConstants.BOBBING_HEIGHT
            } else {
                position.set(target)
                state = PlayerStates.STANDING
                busy = false
                XeenTime.time += 1
            }
        }

        //turn
        else if (state in arrayOf(PlayerStates.TURNING_LEFT, PlayerStates.TURNING_RIGHT)) {
            actionProgress += delta / CommonConstants.PLAYER_SPEED
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