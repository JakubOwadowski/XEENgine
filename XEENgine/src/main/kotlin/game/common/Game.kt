package xeengine.src.main.game.common

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g3d.*
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import xeengine.src.main.common.globals.Globals
import visual.Map
import player.common.Player
import xeengine.src.main.common.constants.global.GlobalDebugConstants.DEBUG_ENABLE
import xeengine.src.main.common.constants.global.GlobalSettingsConstants.SETTING_ALLOW_INPUT_CHAINING
import xeengine.src.main.common.constants.global.GlobalWindowConstants.WINDOW_OFFSET_BOTTOM
import xeengine.src.main.common.constants.global.GlobalWindowConstants.WINDOW_OFFSET_LEFT
import xeengine.src.main.common.constants.global.GlobalWindowConstants.WINDOW_OFFSET_RIGHT
import xeengine.src.main.common.constants.global.GlobalWindowConstants.WINDOW_OFFSET_TOP
import xeengine.src.main.common.constants.global.GlobalWindowConstants.WINDOW_HEIGHT
import xeengine.src.main.common.constants.global.GlobalWindowConstants.WINDOW_WIDTH
import xeengine.src.main.game.debugger.Debugger
import xeengine.src.main.game.utils.generateMap
import xeengine.src.main.game.utils.handleInput
import xeengine.src.main.common.logger.Logger

class Game : ApplicationAdapter() {
    private lateinit var spriteBatch: SpriteBatch
    private lateinit var skyTexture: Texture
    private lateinit var frameTexture: Texture
    private lateinit var modelBatch: ModelBatch
    private lateinit var environment: Environment
    private lateinit var debugger: Debugger

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

        debugger = Debugger()

        player = Player.get()
        player.camera.update()

        skyTexture = Texture(map.skybox)
        frameTexture = Texture("XEENgine/src/main/resources/misc/frame.png")

        generateMap(map, instances)

        Gdx.input.inputProcessor = null
    }

    override fun render() {
        //update player
        if (!player.busy) {
            isKeyPressed = if (!SETTING_ALLOW_INPUT_CHAINING || (!isKeyPressed && SETTING_ALLOW_INPUT_CHAINING)) {
                handleInput(player)
            } else false
        }
        player.update()

        //clear
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        Gdx.gl.glViewport(
            WINDOW_OFFSET_LEFT,
            WINDOW_OFFSET_BOTTOM - WINDOW_OFFSET_TOP,
            WINDOW_WIDTH - WINDOW_OFFSET_RIGHT,
            WINDOW_HEIGHT - WINDOW_OFFSET_BOTTOM
        )

        //draw skybox
        spriteBatch.begin()
        spriteBatch.draw(skyTexture, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        spriteBatch.end()

        //draw environment
        modelBatch.begin(player.camera)
        instances.forEach { modelBatch.render(it, environment) }
        modelBatch.end()

        //set viewpoint
        Gdx.gl.glViewport(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT)

        //draw textures
        spriteBatch.begin()
        spriteBatch.draw(frameTexture, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        spriteBatch.end()

        //draw debug
        if(DEBUG_ENABLE) {
            debugger.drawDebug(spriteBatch, player)
        }

        //update camera
        player.camera.update()
    }

    override fun dispose() {
        modelBatch.dispose()
        spriteBatch.dispose()
        skyTexture.dispose()
        debugger.dispose()
        instances.forEach { instance ->
            instance.model.dispose()
        }
    }
}

