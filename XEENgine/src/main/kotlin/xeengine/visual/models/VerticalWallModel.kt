package xeengine.visual.models

import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute
import xeengine.common.constants.map.MapDimensionsConstants.MAP_CELL_SIZE
import xeengine.common.constants.map.MapDimensionsConstants.MAP_WALL_HEIGHT
import xeengine.common.globals.Globals

class VerticalWallModel(texture: TextureAttribute) {
    val model = Globals.get().modelBuilder.createRect(
        0f, -MAP_WALL_HEIGHT / 2f, -MAP_CELL_SIZE / 2f,
        0f, -MAP_WALL_HEIGHT / 2f, MAP_CELL_SIZE / 2f,
        0f, MAP_WALL_HEIGHT / 2f, MAP_CELL_SIZE / 2f,
        0f, MAP_WALL_HEIGHT / 2f, -MAP_CELL_SIZE / 2f,
        0f, 1f, 0f,
        Material(texture).apply {
            set(IntAttribute.createCullFace(IntAttribute.CullFace.toInt()))
        },
        (Usage.Position or Usage.Normal or Usage.TextureCoordinates).toLong()
    )
}