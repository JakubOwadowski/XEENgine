package visual.models

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import constants.CommonConstants
import globals.Globals

val floorModel = Globals.get().modelBuilder.createBox(
    CommonConstants.CELL_SIZE, CommonConstants.WALL_WIDTH, CommonConstants.CELL_SIZE,
    Material(ColorAttribute.createDiffuse(Color.BROWN)),
    (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal).toLong()
)