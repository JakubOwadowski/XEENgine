package visual.models

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import constants.CommonConstants.CELL_SIZE
import constants.CommonConstants.WALL_WIDTH
import xeengine.src.main.globals.Globals

class RoofModel(/*texture: TextureAttribute*/) {
    val model = Globals.get().modelBuilder.createBox(
        CELL_SIZE, WALL_WIDTH, CELL_SIZE,
        Material(ColorAttribute.createDiffuse(Color.DARK_GRAY)),
        (Usage.Position or Usage.Normal or Usage.TextureCoordinates).toLong()
    )
}