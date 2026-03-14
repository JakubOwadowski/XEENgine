package xeengine.game.common

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g3d.*
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import xeengine.common.cache.Cache
import xeengine.visual.primitives.GameMap
import xeengine.player.common.Player
import xeengine.common.constants.global.GlobalDebugConstants.DEBUG_ENABLE
import xeengine.common.constants.global.GlobalWindowConstants.WINDOW_OFFSET_BOTTOM
import xeengine.common.constants.global.GlobalWindowConstants.WINDOW_OFFSET_LEFT
import xeengine.common.constants.global.GlobalWindowConstants.WINDOW_OFFSET_RIGHT
import xeengine.common.constants.global.GlobalWindowConstants.WINDOW_OFFSET_TOP
import xeengine.common.constants.global.GlobalWindowConstants.WINDOW_HEIGHT
import xeengine.common.constants.global.GlobalWindowConstants.WINDOW_WIDTH
import xeengine.game.debugger.Debugger
import xeengine.game.utils.generateMap
import xeengine.game.utils.handleInput
import xeengine.common.logger.Logger
import xeengine.game.utils.updateMap
import xeengine.visual.primitives.common.TypedInstance

class Game : ApplicationAdapter() {
    private lateinit var spriteBatch: SpriteBatch
    private lateinit var skyTexture: Texture
    private lateinit var frameTexture: Texture
    private lateinit var modelBatch: ModelBatch
    private lateinit var environment: Environment
    private lateinit var debugger: Debugger
    private lateinit var map: GameMap
    private lateinit var player: Player

    private var isKeyPressed = false

    private val instances = mutableListOf<TypedInstance>()

    override fun create() {
        map = Cache.get().map
        Logger.initLog("Loaded map: ${map.name}", Game::class)
        modelBatch = ModelBatch()
        spriteBatch = SpriteBatch()
        environment = Environment()
        environment.set(ColorAttribute.createAmbientLight(Color.WHITE))

        debugger = Debugger()

        player = Player.get()
        player.camera.update()

        skyTexture = Texture(map.skybox)
        frameTexture = Texture("XEENgine/src/main/resources/misc/frame.png")

        generateMap(map, instances, player)

        Gdx.input.inputProcessor = null
    }

    override fun render() {
        //update player
        isKeyPressed = handleInput(player, isKeyPressed)
        player.update()

        //update map
        updateMap(instances, player)

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
        instances.forEach { modelBatch.render(it.instance, environment) }
        modelBatch.end()

        //set viewpoint
        Gdx.gl.glViewport(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT)

        //draw textures
        spriteBatch.begin()
        spriteBatch.draw(frameTexture, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        spriteBatch.end()

        //draw debug
        if (DEBUG_ENABLE) {
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
        instances.forEach { it.instance.model.dispose() }
    }
}

