package visual

import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute
import visual.models.HorizontalWallModel

class Wall {
    constructor()
    constructor(isPresent: Boolean) {
        this.isPresent = isPresent
    }
    constructor(isPresent: Boolean, texture: TextureAttribute) {
        this.isPresent = isPresent
        this.model = ModelInstance(HorizontalWallModel(texture).model)
    }

    var isPresent: Boolean = false
    var model: ModelInstance? = null
}