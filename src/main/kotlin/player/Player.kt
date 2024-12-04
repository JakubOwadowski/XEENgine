package player

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.math.Vector3
import constants.CommonConstants
import constants.DirectionsConstants
import utils.Position
import utils.interpolate
import kotlin.math.sin
import kotlin.times

class Player private constructor() {
    val position = Position()
    val facing = DirectionsConstants.NORTH.get()
    var camera: PerspectiveCamera
    var busy = false
    var actionProgress = 0f
    private var state = PlayerStates.STANDING
    private var target = Position()

    init {
        position.x = 0f
        position.y = 0.5f
        position.z = 0f
        camera = PerspectiveCamera(CommonConstants.PLAYER_FOW, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        camera.position.set(position.x, position.y, position.z)
        camera.lookAt(position.x + facing.x, position.y, position.z + facing.z)
        camera.near = CommonConstants.CAMERA_NEAR
        camera.far = CommonConstants.CAMERA_FAR
    }

    fun getState(): PlayerStates {
        return PlayerStates.WALKING_FORWARD
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
            position.x = interpolate(position.x, target.x, actionProgress)
            position.z = interpolate(position.z, target.z, actionProgress)

            if (actionProgress < 1f) {
                bobbingAmount = sin((actionProgress * CommonConstants.BOBBING_SPEED * Math.PI.toFloat()).toDouble()) * CommonConstants.BOBBING_HEIGHT
            } else {
                position.x = target.x
                position.z = target.z
                state = PlayerStates.STANDING
                busy = false
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