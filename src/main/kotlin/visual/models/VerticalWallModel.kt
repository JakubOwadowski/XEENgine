package visual.models

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import constants.CommonConstants
import globals.Globals

val verticalWallModel = Globals.get().modelBuilder.createBox(
    CommonConstants.WALL_WIDTH, CommonConstants.WALL_HEIGHT, CommonConstants.CELL_SIZE,
    Material(ColorAttribute.createDiffuse(Color.LIGHT_GRAY)),
    (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal).toLong()
)