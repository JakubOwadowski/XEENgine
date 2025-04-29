package visual.models

import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute
import constants.CommonConstants.CELL_SIZE
import constants.CommonConstants.WALL_HEIGHT
import xeengine.src.main.globals.Globals

class HorizontalWallModel(texture: TextureAttribute) {
    val model = Globals.get().modelBuilder.createRect(
        -CELL_SIZE / 2f, -WALL_HEIGHT / 2f, 0f,
        CELL_SIZE / 2f, -WALL_HEIGHT / 2f, 0f,
        CELL_SIZE / 2f, WALL_HEIGHT / 2f, 0f,
        -CELL_SIZE / 2f, WALL_HEIGHT / 2f, 0f,
        0f, 1f, 0f,
        Material(texture).apply {
            set(IntAttribute.createCullFace(IntAttribute.CullFace.toInt()))
        },
        (Usage.Position or Usage.Normal or Usage.TextureCoordinates).toLong()
    )
}