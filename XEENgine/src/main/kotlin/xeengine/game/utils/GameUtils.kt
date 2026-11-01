package xeengine.game.utils

import com.badlogic.gdx.Gdx
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
                map.getCell(x, y, z).draw(x, y, z, instances)
            }
        }
    }
}

fun updateMap(instances: MutableList<TypedInstance>, player: Player) {
    for (instance in instances) {
        when (instance.type) {
            InstanceTypeEnum.AMBIENT -> {
                val pos = Vector3(instance.x, 0f, instance.z)
                instance.instance.transform.set(cylindricalBillboardTransform(pos, player.camera.position))
            }
            // Show only the face the player is currently looking at.
            // dot(facing, faceOutwardNormal) < 0 means the player faces toward that face.
            // Face normals: NORTH=(0,0,1), SOUTH=(0,0,-1), EAST=(1,0,0), WEST=(-1,0,0)
            InstanceTypeEnum.MAP3D_NORTH,
            InstanceTypeEnum.MAP3D_SOUTH,
            InstanceTypeEnum.MAP3D_EAST,
            InstanceTypeEnum.MAP3D_WEST -> {
                val f = player.getFacing()
                val alpha = maxOf(0f, when (instance.type) {
                    InstanceTypeEnum.MAP3D_NORTH -> f.z
                    InstanceTypeEnum.MAP3D_SOUTH -> -f.z
                    InstanceTypeEnum.MAP3D_EAST  -> -f.x
                    InstanceTypeEnum.MAP3D_WEST  ->  f.x
                    else -> 0f
                })
                instance.visible = alpha > 0f
                instance.ownedBlending?.opacity = alpha
            }
            else -> { /* static geometry — no per-frame update needed */ }
        }
    }
}

/**
 * Builds a transform for a cylindrical billboard at [pos] that faces [cameraPos].
 *
 * "Cylindrical" means the billboard rotates only around the Y axis — it always
 * stands upright regardless of camera elevation.
 *
 * Strategy: rotate the billboard's default +Z face toward the camera by using
 * setToRotation(from, to). This is explicit and correct without any transpose
 * tricks (setToLookAt produces a view/camera matrix, not a model matrix, so
 * naively using it requires a transpose to invert the rotation — easy to
 * accidentally remove and hard to spot).
 */
private fun cylindricalBillboardTransform(pos: Vector3, cameraPos: Vector3): Matrix4 {
    // Project the camera position onto the billboard's Y plane so elevation
    // differences don't tilt the billboard forward/backward.
    val dir = Vector3(cameraPos.x, pos.y, cameraPos.z).sub(pos).nor()
    return Matrix4().setToRotation(Vector3.Z, dir).setTranslation(pos)
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