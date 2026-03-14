package xeengine.visual.primitives

import com.badlogic.gdx.graphics.g3d.ModelInstance
import xeengine.visual.models.HorizontalWallModel
import xeengine.visual.textures.Textures

class Map3dWall {
    var alpha: Float = 0f
    var model: ModelInstance = ModelInstance(HorizontalWallModel(Textures.get("TEXTURE_WALL")).model)
}