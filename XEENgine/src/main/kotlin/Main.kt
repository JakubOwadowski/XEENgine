package xeengine.src.main

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import xeengine.src.main.game.Game
import xeengine.src.main.logger.Logger

fun main() {
    val config = Lwjgl3ApplicationConfiguration()
    Logger.cleanLogs()
    config.setTitle("XEENgine")
    config.setWindowedMode(856, 516)
    config.setResizable(true)
    Lwjgl3Application(Game(), config)
}