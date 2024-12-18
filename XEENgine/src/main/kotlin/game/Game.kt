package xeengine.src.main.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g3d.*
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import constants.CommonConstants.ALLOW_INPUT_CHAIN
import constants.CommonConstants.ENABLE_DEBUG
import constants.KeyboardConstants
import globals.Globals
import visual.Map
import player.Player
import xeengine.src.main.XeenTime.XeenTime

class Game : ApplicationAdapter() {
    private lateinit var spriteBatch: SpriteBatch
    private lateinit var skyTexture: Texture
    private lateinit var modelBatch: ModelBatch
    private lateinit var environment: Environment
    private lateinit var debugFont: BitmapFont

    private lateinit var map: Map

    private val instances = mutableListOf<ModelInstance>()
    private var isKeyPressed = false

    private lateinit var player: Player

    override fun create() {
        map = Globals.get().map
        modelBatch = ModelBatch()
        spriteBatch = SpriteBatch()
        environment = Environment()
        environment.set(ColorAttribute.createAmbientLight(Color(100f, 100f, 100f, 1f)))

        player = Player.get()
        player.camera.update()

        skyTexture = Texture("XEENgine/src/main/resources/misc/skybox.png")
        debugFont = BitmapFont()
        debugFont.color = Color.WHITE

        generateMap()

        Gdx.input.inputProcessor = null
    }

    private fun generateMap() {
        for (y in map.getLevelIndices()) {
            for (x in map.getColumnIndices(y)) {
                for (z in map.getRowIndices(x, y)) {
                    map.getCell(x, y, z).draw(x, y, z, instances)
                }
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

        spriteBatch.begin()
        spriteBatch.draw(skyTexture, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        spriteBatch.end()

        modelBatch.begin(player.camera)
        instances.forEach { modelBatch.render(it, environment) }
        modelBatch.end()

        if(ENABLE_DEBUG) {
            drawDebug()
        }

        player.camera.update()
    }

    private fun handleInput() {
        if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_W)) {
            if (!ALLOW_INPUT_CHAIN || (!isKeyPressed && ALLOW_INPUT_CHAIN)) player.walkForward()
            isKeyPressed = true
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_S)) {
            if (!ALLOW_INPUT_CHAIN || (!isKeyPressed && ALLOW_INPUT_CHAIN)) player.walkBackward()
            isKeyPressed = true
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_D)) {
            if (!ALLOW_INPUT_CHAIN || (!isKeyPressed && ALLOW_INPUT_CHAIN)) player.strafeRight()
            isKeyPressed = true
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_A)) {
            if (!ALLOW_INPUT_CHAIN || (!isKeyPressed && ALLOW_INPUT_CHAIN)) player.strafeLeft()
            isKeyPressed = true
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_Q)) {
            if (!ALLOW_INPUT_CHAIN || (!isKeyPressed && ALLOW_INPUT_CHAIN)) player.turnRight()
            isKeyPressed = true
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_E)) {
            if (!ALLOW_INPUT_CHAIN || (!isKeyPressed && ALLOW_INPUT_CHAIN)) player.turnLeft()
            isKeyPressed = true
        } else {
            isKeyPressed = false
        }
    }

    private fun drawDebug() {
        spriteBatch.begin()
        var debugText = "XEENgine v" + Globals.get().version + "\n"
        debugText += "Player position: X: " + player.getXZPosition().x + " Z " + player.getXZPosition().z + "\n"
        debugText += "Current time: " + XeenTime.time + " (Year: " + XeenTime.getYear() + ", Month: " + XeenTime.getMonth() + ", Week: " + XeenTime.getWeek() + ", Day: " + XeenTime.getDayOfMonth().number + " - " + XeenTime.getDayOfMonth().name + " " + XeenTime.getHours() + ")\n"
        debugText += "Map: " + Globals.get().map.name + "\n"
        val xPosition = 10f
        val yPosition = Gdx.graphics.height - 10f

        debugFont.color = Color.BLACK
        debugFont.draw(spriteBatch, debugText, xPosition - 1, yPosition)
        debugFont.draw(spriteBatch, debugText, xPosition + 1, yPosition)
        debugFont.draw(spriteBatch, debugText, xPosition, yPosition - 1)
        debugFont.draw(spriteBatch, debugText, xPosition, yPosition + 1)

        debugFont.color = Color.WHITE
        debugFont.draw(spriteBatch, debugText, xPosition, yPosition)
        spriteBatch.end()
    }

    override fun dispose() {
        modelBatch.dispose()
        spriteBatch.dispose()
        skyTexture.dispose()
        map.dispose()
        debugFont.dispose()
    }
}
