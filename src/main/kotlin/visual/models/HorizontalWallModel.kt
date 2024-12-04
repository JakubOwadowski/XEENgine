package visual.models

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import constants.CommonConstants
import globals.Globals

val horizontalWallModel = Globals.get().modelBuilder.createBox(
    CommonConstants.CELL_SIZE, CommonConstants.WALL_HEIGHT, CommonConstants.WALL_WIDTH,
    Material(ColorAttribute.createDiffuse(Color.LIGHT_GRAY)),
    (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal).toLong()
)