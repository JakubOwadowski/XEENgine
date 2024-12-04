package xeen.src.main.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g3d.*
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import constants.KeyboardConstants
import constants.CommonConstants
import visual.Map
import player.Player
import com.badlogic.gdx.math.Vector3
import constants.DirectionsConstants
import maps.VertigoCityMap
import kotlin.math.sin

class Game : ApplicationAdapter() {
    private lateinit var modelBatch: ModelBatch
    private lateinit var environment: Environment

    private lateinit var map: Map

    private val instances = mutableListOf<ModelInstance>()

    private lateinit var player: Player

    // Movement and rotation state
    private var isActionInProgress = false
    private var actionProgress = 0f
    private val actionDuration = 0.15f // Time for each move/rotation to complete

    private var targetPosition: Vector3? = null
    private var targetDirection: Vector3? = null
    private var turnProgress = 0f

    override fun create() {
        map = VertigoCityMap
        modelBatch = ModelBatch()
        environment = Environment()
        environment.set(ColorAttribute.createAmbientLight(Color(100f, 100f, 100f, 1f)))

        player = Player(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        player.camera.update()

        generateMap()

        // Disable mouse input
        Gdx.input.inputProcessor = null
    }

    private fun generateMap() {
        for (x in map.getColumnIndices()) {
            for (z in map.getRowIndices(x)) {
                map.getCell(x, z).draw(x, z, instances)
            }
        }
    }

    override fun render() {
        println(player.camera.position)
        if (isActionInProgress) {
            processAction()
        } else {
            handleInput()
        }

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        modelBatch.begin(player.camera)
        instances.forEach { modelBatch.render(it, environment) }
        modelBatch.end()

        player.camera.update()
    }

    private fun handleInput() {
        if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_W)) {
            startMove(player.facing)
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_S)) {
            startMove(Vector3(-player.facing.x, 0f, -player.facing.z))
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_D)) {
            startMove(Vector3(-player.facing.z, 0f, player.facing.x))
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_A)) {
            startMove(Vector3(player.facing.z, 0f, -player.facing.x))
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_Q)) {
            startTurn(-90f)
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_E)) {
            startTurn(90f)
        }
    }

    private fun startMove(direction: Vector3) {
        if (isActionInProgress) return
        isActionInProgress = true
        actionProgress = 0f

        targetPosition = Vector3(
            player.position.x + direction.x * CommonConstants.PLAYER_SPEED,
            player.position.y,
            player.position.z + direction.z * CommonConstants.PLAYER_SPEED
        )
    }

    private fun startTurn(angle: Float) {
        if (isActionInProgress) return
        isActionInProgress = true
        actionProgress = 0f
        turnProgress = 0f
        // Set target direction based on the current facing direction and turn angle
        targetDirection = when (player.facing) {
            DirectionsConstants.NORTH.get() -> when (angle) {
                -90f -> DirectionsConstants.EAST.get()
                90f -> DirectionsConstants.WEST.get()
                else -> DirectionsConstants.WEST.get()
            }
            DirectionsConstants.EAST.get() -> when (angle) {
                -90f -> DirectionsConstants.SOUTH.get()
                90f -> DirectionsConstants.NORTH.get()
                else -> DirectionsConstants.NORTH.get()
            }
            DirectionsConstants.SOUTH.get() -> when (angle) {
                -90f -> DirectionsConstants.WEST.get()
                90f -> DirectionsConstants.EAST.get()
                else -> DirectionsConstants.EAST.get()
            }
            DirectionsConstants.WEST.get() -> when (angle) {
                -90f -> DirectionsConstants.NORTH.get()
                90f -> DirectionsConstants.SOUTH.get()
                else -> DirectionsConstants.SOUTH.get()
            }
            else -> player.facing.cpy() // Fallback case, should never hit here
        }
    }

    private fun processAction() {
        val delta = Gdx.graphics.deltaTime
        actionProgress += delta / actionDuration
        var bobbingAmount = 0.0

        // Handle movement
        if (targetPosition != null) {
            val interpolatedX = interpolate(player.position.x, targetPosition!!.x, actionProgress)
            val interpolatedZ = interpolate(player.position.z, targetPosition!!.z, actionProgress)

            player.position.x = interpolatedX
            player.position.z = interpolatedZ

            if (actionProgress < 1f) {
                bobbingAmount = sin((actionProgress * CommonConstants.BOBBING_SPEED * Math.PI.toFloat()).toDouble()) * CommonConstants.BOBBING_HEIGHT
            }

            if (actionProgress >= 1f) {
                player.position.x = targetPosition!!.x
                player.position.z = targetPosition!!.z
                targetPosition = null
                isActionInProgress = false
            }
        }

        // Handle turning
        if (targetDirection != null) {
            turnProgress += delta / actionDuration
            val interpolatedDirection = player.facing.cpy().lerp(targetDirection, turnProgress)

            // Update the facing direction smoothly during the turn
            player.facing.set(interpolatedDirection)

            // If the turn is complete, set the facing direction to the target and reset states
            if (turnProgress >= 1f) {
                player.facing.set(targetDirection)
                targetDirection = null
                isActionInProgress = false
            }
        }

        // Update the player.camera position with bobbing effect
        player.camera.position.set(player.position.x, (player.position.y + bobbingAmount).toFloat(), player.position.z)
        player.camera.lookAt(player.position.x + player.facing.x, (player.position.y + bobbingAmount).toFloat(), player.position.z + player.facing.z)
    }

    private fun interpolate(start: Float, end: Float, alpha: Float): Float {
        return start + (end - start) * alpha
    }

    override fun dispose() {
        modelBatch.dispose()
        map.dispose()
    }
}

