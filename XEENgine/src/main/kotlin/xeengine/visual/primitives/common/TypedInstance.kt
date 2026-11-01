package xeengine.visual.primitives.common

import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute
import xeengine.visual.primitives.enums.InstanceTypeEnum

class TypedInstance(val instance: ModelInstance, val type: InstanceTypeEnum, val x: Float, val z: Float) {
    var visible = true
    // Only set for MAP3D face types. Stored separately so alpha changes never
    // bleed through shared material references to unrelated instances.
    val ownedBlending: BlendingAttribute? =
        if (type in MAP3D_TYPES) BlendingAttribute(true, 1f).also { instance.materials[0].set(it) }
        else null

    companion object {
        private val MAP3D_TYPES = setOf(
            InstanceTypeEnum.MAP3D_NORTH,
            InstanceTypeEnum.MAP3D_SOUTH,
            InstanceTypeEnum.MAP3D_EAST,
            InstanceTypeEnum.MAP3D_WEST
        )
    }
}