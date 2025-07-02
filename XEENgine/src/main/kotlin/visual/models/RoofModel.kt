package visual.models

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import constants.MapDimensionsConstants.MAP_CELL_SIZE
import xeengine.src.main.common.globals.Globals

class RoofModel(/*texture: TextureAttribute*/) {
    val model = Globals.get().modelBuilder.createRect(
        -MAP_CELL_SIZE/2, 0f, -MAP_CELL_SIZE/2,
        MAP_CELL_SIZE/2, 0f, -MAP_CELL_SIZE/2,
        MAP_CELL_SIZE/2, 0f, MAP_CELL_SIZE/2,
        -MAP_CELL_SIZE/2, 0f, MAP_CELL_SIZE/2,
        0f, 1f, 0f,
        Material(ColorAttribute.createDiffuse(Color.DARK_GRAY)),
        (Usage.Position or Usage.Normal or Usage.TextureCoordinates).toLong()
    )
}
