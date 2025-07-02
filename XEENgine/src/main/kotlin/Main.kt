package xeengine.src.main

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import xeengine.src.main.common.constants.global.GlobalWindowConstants.WINDOW_TITLE
import xeengine.src.main.common.constants.global.GlobalWindowConstants.WINDOW_HEIGHT
import xeengine.src.main.common.constants.global.GlobalWindowConstants.WINDOW_WIDTH
import xeengine.src.main.game.Game
import xeengine.src.main.logger.Logger

fun main() {
    val window = Lwjgl3ApplicationConfiguration()
    Logger.cleanLogs()
    window.setTitle(WINDOW_TITLE)
    window.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT)
    window.setResizable(false)
    Lwjgl3Application(Game(), window)
}