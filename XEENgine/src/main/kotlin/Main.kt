package xeengine.src.main

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import xeengine.src.main.constants.WindowDimensionConstants.WINDOW_HEIGHT
import xeengine.src.main.constants.WindowDimensionConstants.WINDOW_WIDTH
import xeengine.src.main.game.Game
import xeengine.src.main.logger.Logger

fun main() {
    val config = Lwjgl3ApplicationConfiguration()
    Logger.cleanLogs()
    config.setTitle("XEENgine")
    config.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT)
    config.setResizable(false)
    Lwjgl3Application(Game(), config)
}