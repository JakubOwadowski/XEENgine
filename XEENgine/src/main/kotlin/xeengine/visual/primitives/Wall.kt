package xeengine.visual.primitives

import com.badlogic.gdx.graphics.g3d.ModelInstance
import xeengine.visual.models.HorizontalWallModel
import xeengine.visual.textures.Textures

class Wall {
    var isPresent: Boolean = false
    var model: ModelInstance = ModelInstance(HorizontalWallModel(Textures.get("TEXTURE_WALL")).model)
}