package xeengine.game.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g3d.ModelInstance
import xeengine.common.constants.global.GlobalSettingsConstants.SETTING_ALLOW_INPUT_CHAINING
import xeengine.player.common.Player
import xeengine.visual.primitives.Map
import xeengine.common.settings.common.Settings

fun generateMap(map: Map, instances: MutableList<ModelInstance>) {
    for (y in map.getLevelIndices()) {
        for (x in map.getColumnIndices(y)) {
            for (z in map.getRowIndices(x, y)) {
                map.getCell(x, y, z).draw(x, y, z, instances)
            }
        }
    }
}

fun handleInput(player: Player, isKeyPressed: Boolean): Boolean {
    var isKeyPressed = isKeyPressed
    val isInputAllowed = !SETTING_ALLOW_INPUT_CHAINING || !isKeyPressed

    if (!player.busy) {
        when {
            Gdx.input.isKeyPressed(Settings.keyForward) -> {
                if (isInputAllowed) player.walkForward()
                isKeyPressed = true
            }
            Gdx.input.isKeyPressed(Settings.keyBackward) -> {
                if (isInputAllowed) player.walkBackward()
                isKeyPressed = true
            }
            Gdx.input.isKeyPressed(Settings.keyMoveRight) -> {
                if (isInputAllowed) player.moveRight()
                isKeyPressed = true
            }
            Gdx.input.isKeyPressed(Settings.keyMoveLeft) -> {
                if (isInputAllowed) player.moveLeft()
                isKeyPressed = true
            }
            Gdx.input.isKeyPressed(Settings.keyRotateLeft) -> {
                if (isInputAllowed) player.rotateLeft()
                isKeyPressed = true
            }
            Gdx.input.isKeyPressed(Settings.keyRotateRight) -> {
                if (isInputAllowed) player.rotateRight()
                isKeyPressed = true
            }
            else -> isKeyPressed = false
        }
    }

    return isKeyPressed
}