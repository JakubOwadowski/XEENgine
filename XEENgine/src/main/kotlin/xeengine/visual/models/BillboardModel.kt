package xeengine.visual.models

import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute
import xeengine.common.cache.Cache
import xeengine.common.constants.map.MapDimensionsConstants.MAP_CELL_SIZE

class BillboardModel(texture: TextureAttribute) {
    val model: Model = Cache.get().modelBuilder.createRect(
        -MAP_CELL_SIZE / 2f, 0f, 0f,
        MAP_CELL_SIZE / 2f, 0f, 0f,
        MAP_CELL_SIZE / 2f, MAP_CELL_SIZE, 0f,
        -MAP_CELL_SIZE / 2f, MAP_CELL_SIZE, 0f,
        0f, 0f, -1f,
        Material(texture).apply {
            set(BlendingAttribute(true, 1f))
            set(IntAttribute.createCullFace(IntAttribute.CullFace.toInt()))
        },
        (Usage.Position or Usage.Normal or Usage.TextureCoordinates).toLong()
    )
}
