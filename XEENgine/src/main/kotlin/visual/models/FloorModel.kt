package visual.models

import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute
import constants.CommonConstants.CELL_SIZE
import xeengine.src.main.globals.Globals

class FloorModel(texture: TextureAttribute) {
    val model = Globals.get().modelBuilder.createRect(
        -CELL_SIZE / 2f, 0f, -CELL_SIZE / 2f,
        CELL_SIZE / 2f, 0f, -CELL_SIZE / 2f,
        CELL_SIZE / 2f, 0f, CELL_SIZE / 2f,
        -CELL_SIZE / 2f, 0f, CELL_SIZE / 2f,
        0f, 1f, 0f,
        Material(texture).apply {
            set(IntAttribute.createCullFace(IntAttribute.CullFace.toInt()))
        },
        (Usage.Position or Usage.Normal or Usage.TextureCoordinates).toLong()
    )
}