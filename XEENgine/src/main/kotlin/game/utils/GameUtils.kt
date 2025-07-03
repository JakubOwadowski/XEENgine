package xeengine.src.main.game.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g3d.ModelInstance
import player.common.Player
import visual.Map
import xeengine.src.main.common.settings.common.Settings

fun generateMap(map: Map, instances: MutableList<ModelInstance>) {
    for (y in map.getLevelIndices()) {
        for (x in map.getColumnIndices(y)) {
            for (z in map.getRowIndices(x, y)) {
                map.getCell(x, y, z).draw(x, y, z, instances)
            }
        }
    }
}

fun handleInput(player: Player): Boolean {
    var isKeyPressed = true

    when {
        Gdx.input.isKeyPressed(Settings.keyForward) -> player.walkForward()
        Gdx.input.isKeyPressed(Settings.keyBackward) -> player.walkBackward()
        Gdx.input.isKeyPressed(Settings.keyMoveRight) -> player.strafeRight()
        Gdx.input.isKeyPressed(Settings.keyMoveLeft) -> player.strafeLeft()
        Gdx.input.isKeyPressed(Settings.keyRotateLeft) -> player.turnRight()
        Gdx.input.isKeyPressed(Settings.keyRotateRight) -> player.turnLeft()
        else -> isKeyPressed = false
    }

    return isKeyPressed
}