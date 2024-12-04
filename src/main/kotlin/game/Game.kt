package xeengine.src.main.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g3d.*
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import constants.KeyboardConstants
import visual.Map
import player.Player
import maps.VertigoCityMap

class Game : ApplicationAdapter() {
    private lateinit var modelBatch: ModelBatch
    private lateinit var environment: Environment

    private lateinit var map: Map

    private val instances = mutableListOf<ModelInstance>()

    private lateinit var player: Player

    override fun create() {
        map = VertigoCityMap
        modelBatch = ModelBatch()
        environment = Environment()
        environment.set(ColorAttribute.createAmbientLight(Color(100f, 100f, 100f, 1f)))

        player = Player.get()
        player.camera.update()

        generateMap()

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
        if (!player.busy) {
            handleInput()
        }
        player.update()

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        modelBatch.begin(player.camera)
        instances.forEach { modelBatch.render(it, environment) }
        modelBatch.end()

        player.camera.update()
    }

    private fun handleInput() {
        if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_W)) {
            player.walkForward()
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_S)) {
            player.walkBackward()
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_D)) {
            player.strafeRight()
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_A)) {
            player.strafeLeft()
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_Q)) {
            player.turnRight()
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_E)) {
            player.turnLeft()
        }
    }

    override fun dispose() {
        modelBatch.dispose()
        map.dispose()
    }
}

