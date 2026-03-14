package xeengine.game.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3
import xeengine.common.constants.global.GlobalSettingsConstants.SETTING_ALLOW_INPUT_CHAINING
import xeengine.player.common.Player
import xeengine.visual.primitives.GameMap
import xeengine.common.settings.common.Settings
import xeengine.visual.primitives.common.TypedInstance
import xeengine.visual.primitives.enums.InstanceTypeEnum

fun generateMap(map: GameMap, instances: MutableList<TypedInstance>, player: Player) {
    for (y in map.getLevelIndices()) {
        for (x in map.getColumnIndices(y)) {
            for (z in map.getRowIndices(x, y)) {
                map.getCell(x, y, z).draw(x, y, z, instances, player)
            }
        }
    }
}

fun updateMap(instances: MutableList<TypedInstance>, player: Player) {
    for (instance in instances) {
        val up = Vector3.Y
        if (instance.type == InstanceTypeEnum.AMBIENT) {
            val ambientObjectInstance = instance.instance
            val pos = Vector3(instance.x, 0f, instance.z)
            val dir = Vector3(player.camera.position.x, pos.y, player.camera.position.z).sub(pos).nor()
            val tmp = Matrix4()
            tmp.setToLookAt(dir, up).tra()
            tmp.setTranslation(pos)
            ambientObjectInstance.transform.set(tmp)
        }
    }
}

fun handleInput(player: Player, isKeyPressed: Boolean): Boolean {
    var newIsKeyPressed = isKeyPressed
    val isInputAllowed = !SETTING_ALLOW_INPUT_CHAINING || !newIsKeyPressed

    if (!player.busy) {
        when {
            Gdx.input.isKeyPressed(Settings.keyForward) -> {
                if (isInputAllowed) player.walkForward()
                newIsKeyPressed = true
            }
            Gdx.input.isKeyPressed(Settings.keyBackward) -> {
                if (isInputAllowed) player.walkBackward()
                newIsKeyPressed = true
            }
            Gdx.input.isKeyPressed(Settings.keyMoveRight) -> {
                if (isInputAllowed) player.moveRight()
                newIsKeyPressed = true
            }
            Gdx.input.isKeyPressed(Settings.keyMoveLeft) -> {
                if (isInputAllowed) player.moveLeft()
                newIsKeyPressed = true
            }
            Gdx.input.isKeyPressed(Settings.keyRotateLeft) -> {
                if (isInputAllowed) player.rotateLeft()
                newIsKeyPressed = true
            }
            Gdx.input.isKeyPressed(Settings.keyRotateRight) -> {
                if (isInputAllowed) player.rotateRight()
                newIsKeyPressed = true
            }
            else -> newIsKeyPressed = false
        }
    }

    return newIsKeyPressed
}