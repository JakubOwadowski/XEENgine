package xeen.src.main

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import xeen.src.main.game.Game

fun main() {
    val config = Lwjgl3ApplicationConfiguration()
    config.setTitle("My Game")
    config.setWindowedMode(800, 600)
    config.setResizable(true)
    Lwjgl3Application(Game(), config)
}