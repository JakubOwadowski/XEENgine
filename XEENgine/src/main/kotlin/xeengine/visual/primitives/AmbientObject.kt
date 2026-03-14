package xeengine.visual.primitives

import com.badlogic.gdx.graphics.g3d.ModelInstance
import xeengine.visual.models.BillboardModel
import xeengine.visual.textures.Textures

class AmbientObject {
    var x = 1f
    var z = 1f
    var model: ModelInstance = ModelInstance(BillboardModel(Textures.get("TEXTURE_WALL")).model)
}