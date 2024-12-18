package xeengine.src.main

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import xeengine.src.main.game.Game

fun main() {
    val config = Lwjgl3ApplicationConfiguration()
    config.setTitle("XEENgine")
    config.setWindowedMode(856, 516)
    config.setResizable(true)
    Lwjgl3Application(Game(), config)
}