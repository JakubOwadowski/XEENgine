package visual

import com.badlogic.gdx.graphics.g3d.ModelInstance
import visual.models.HorizontalWallModel
import xeengine.src.main.visual.textures.Textures

class Wall {
    var isPresent: Boolean = false
    var model: ModelInstance = ModelInstance(HorizontalWallModel(Textures.get("TEXTURE_WALL")).model)
}