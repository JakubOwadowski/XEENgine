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
import xeengine.src.main.common.constants.keys.KeyboardConstants
import xeengine.src.main.globals.Globals
import visual.Map
import player.Player
import xeengine.src.main.XeenTime.XeenTime
import xeengine.src.main.common.constants.global.GlobalSettingsConstants.SETTING_ALLOW_INPUT_CHAINING
import xeengine.src.main.common.constants.global.GlobalSettingsConstants.SETTING_ENABLE_DEBUG
import xeengine.src.main.common.constants.global.GlobalWindowConstants.WINDOW_OFFSET_BOTTOM
import xeengine.src.main.common.constants.global.GlobalWindowConstants.WINDOW_OFFSET_LEFT
import xeengine.src.main.common.constants.global.GlobalWindowConstants.WINDOW_OFFSET_RIGHT
import xeengine.src.main.common.constants.global.GlobalWindowConstants.WINDOW_OFFSET_TOP
import xeengine.src.main.common.constants.global.GlobalWindowConstants.WINDOW_HEIGHT
import xeengine.src.main.common.constants.global.GlobalWindowConstants.WINDOW_WIDTH
import xeengine.src.main.logger.Logger

class Game : ApplicationAdapter() {
    private lateinit var spriteBatch: SpriteBatch
    private lateinit var skyTexture: Texture
    private lateinit var frameTexture: Texture
    private lateinit var modelBatch: ModelBatch
    private lateinit var environment: Environment
    private lateinit var debugFont: BitmapFont

    private lateinit var map: Map

    private val instances = mutableListOf<ModelInstance>()
    private var isKeyPressed = false

    private lateinit var player: Player

    override fun create() {
        map = Globals.get().map
        Logger.initLog("Loaded map: ${map.name}", Game::class)
        modelBatch = ModelBatch()
        spriteBatch = SpriteBatch()
        environment = Environment()
        environment.set(ColorAttribute.createAmbientLight(Color(100f, 100f, 100f, 1f)))

        player = Player.get()
        player.camera.update()

        skyTexture = Texture(map.skybox)
        frameTexture = Texture("XEENgine/src/main/resources/misc/frame.png")
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

        Gdx.gl.glViewport(WINDOW_OFFSET_LEFT,  WINDOW_OFFSET_BOTTOM - WINDOW_OFFSET_TOP, WINDOW_WIDTH - WINDOW_OFFSET_RIGHT, WINDOW_HEIGHT - WINDOW_OFFSET_BOTTOM)

        spriteBatch.begin()
        spriteBatch.draw(skyTexture, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        spriteBatch.end()

        modelBatch.begin(player.camera)
        instances.forEach { modelBatch.render(it, environment) }
        modelBatch.end()

        Gdx.gl.glViewport(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT)

        spriteBatch.begin()
        spriteBatch.draw(frameTexture, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        spriteBatch.end()

        if(SETTING_ENABLE_DEBUG) {
            drawDebug()
        }

        player.camera.update()
    }

    private fun handleInput() {
        if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_W)) {
            if (!SETTING_ALLOW_INPUT_CHAINING || (!isKeyPressed && SETTING_ALLOW_INPUT_CHAINING)) player.walkForward()
            isKeyPressed = true
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_S)) {
            if (!SETTING_ALLOW_INPUT_CHAINING || (!isKeyPressed && SETTING_ALLOW_INPUT_CHAINING)) player.walkBackward()
            isKeyPressed = true
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_D)) {
            if (!SETTING_ALLOW_INPUT_CHAINING || (!isKeyPressed && SETTING_ALLOW_INPUT_CHAINING)) player.strafeRight()
            isKeyPressed = true
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_A)) {
            if (!SETTING_ALLOW_INPUT_CHAINING || (!isKeyPressed && SETTING_ALLOW_INPUT_CHAINING)) player.strafeLeft()
            isKeyPressed = true
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_Q)) {
            if (!SETTING_ALLOW_INPUT_CHAINING || (!isKeyPressed && SETTING_ALLOW_INPUT_CHAINING)) player.turnRight()
            isKeyPressed = true
        } else if (Gdx.input.isKeyPressed(KeyboardConstants.KEY_E)) {
            if (!SETTING_ALLOW_INPUT_CHAINING || (!isKeyPressed && SETTING_ALLOW_INPUT_CHAINING)) player.turnLeft()
            isKeyPressed = true
        } else {
            isKeyPressed = false
        }
    }

    private fun drawDebug() {
        spriteBatch.begin()
        var debugText = "XEENgine v${Globals.get().version}\n"
        debugText += "Player position: X: ${player.getXZPosition().x}, Z: ${player.getXZPosition().z}\n"
        debugText += "Current time: ${XeenTime.time} (Year: ${XeenTime.getYear()}, Month: ${XeenTime.getMonth()}, Week: ${XeenTime.getWeek()}, Day: ${XeenTime.getDayOfMonth().number} - ${XeenTime.getDayOfMonth().name} ${XeenTime.getHours()})\n"
        debugText += "Map: ${Globals.get().map.name}\n"
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
        debugFont.dispose()
        instances.forEach { instance ->
            instance.model.dispose()
        }
    }
}

