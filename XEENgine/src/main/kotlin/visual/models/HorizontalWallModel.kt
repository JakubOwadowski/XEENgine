package visual.models

import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute
import constants.MapDimensionsConstants.MAP_CELL_SIZE
import constants.MapDimensionsConstants.MAP_WALL_HEIGHT
import xeengine.src.main.common.globals.Globals

class HorizontalWallModel(texture: TextureAttribute) {
    val model = Globals.get().modelBuilder.createRect(
        -MAP_CELL_SIZE / 2f, -MAP_WALL_HEIGHT / 2f, 0f,
        MAP_CELL_SIZE / 2f, -MAP_WALL_HEIGHT / 2f, 0f,
        MAP_CELL_SIZE / 2f, MAP_WALL_HEIGHT / 2f, 0f,
        -MAP_CELL_SIZE / 2f, MAP_WALL_HEIGHT / 2f, 0f,
        0f, 1f, 0f,
        Material(texture).apply {
            set(IntAttribute.createCullFace(IntAttribute.CullFace.toInt()))
        },
        (Usage.Position or Usage.Normal or Usage.TextureCoordinates).toLong()
    )
}