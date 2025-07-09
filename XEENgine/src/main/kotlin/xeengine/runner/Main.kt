package xeengine.runner

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import xeengine.common.constants.global.GlobalWindowConstants.WINDOW_TITLE
import xeengine.common.constants.global.GlobalWindowConstants.WINDOW_HEIGHT
import xeengine.common.constants.global.GlobalWindowConstants.WINDOW_WIDTH
import xeengine.game.common.Game
import xeengine.common.logger.Logger

fun main() {
    val window = Lwjgl3ApplicationConfiguration()
    Logger.cleanLogs()
    window.setTitle(WINDOW_TITLE)
    window.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT)
    window.setResizable(false)
    Lwjgl3Application(Game(), window)
}