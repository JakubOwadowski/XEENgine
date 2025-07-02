package xeengine.src.main.game.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g3d.ModelInstance
import player.common.Player
import visual.Map
import xeengine.src.main.common.constants.keys.KeyboardConstants

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
        Gdx.input.isKeyPressed(KeyboardConstants.KEY_FORWARD) -> player.walkForward()
        Gdx.input.isKeyPressed(KeyboardConstants.KEY_BACKWARD) -> player.walkBackward()
        Gdx.input.isKeyPressed(KeyboardConstants.KEY_MOVE_RIGHT) -> player.strafeRight()
        Gdx.input.isKeyPressed(KeyboardConstants.KEY_MOVE_LEFT) -> player.strafeLeft()
        Gdx.input.isKeyPressed(KeyboardConstants.KEY_ROTATE_LEFT) -> player.turnRight()
        Gdx.input.isKeyPressed(KeyboardConstants.KEY_ROTATE_RIGHT) -> player.turnLeft()
        else -> isKeyPressed = false
    }

    return isKeyPressed
}