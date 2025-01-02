package visual.models

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute
import constants.CommonConstants.CELL_SIZE
import constants.CommonConstants.WALL_WIDTH
import xeengine.src.main.globals.Globals
import xeengine.src.main.visual.textures.Textures

class FloorModel(texture: TextureAttribute) {
    val model = Globals.get().modelBuilder.createBox(
        CELL_SIZE, WALL_WIDTH, CELL_SIZE,
        Material(texture),
        (Usage.Position or Usage.Normal or Usage.TextureCoordinates).toLong()
    )
}