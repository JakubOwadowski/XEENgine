package xeengine.visual.models

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Mesh
import com.badlogic.gdx.graphics.VertexAttribute
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute
import xeengine.common.constants.map.MapDimensionsConstants.MAP_CELL_SIZE
import xeengine.common.constants.map.MapDimensionsConstants.MAP_WALL_HEIGHT
import xeengine.common.globals.Globals

class HorizontalTriangleWallModel(texture: TextureAttribute) {
    val model: Model

    init {
        val builder = Globals.get().modelBuilder

        val vertices = floatArrayOf(
            -MAP_CELL_SIZE/2, -MAP_WALL_HEIGHT/2, 0f,    0f, 0f, 1f, 0f, 0f,
            MAP_CELL_SIZE/2, -MAP_WALL_HEIGHT/2, 0f,    0f, 0f, 1f, 1f, 0f,
            -MAP_CELL_SIZE/2, MAP_WALL_HEIGHT/2, 0f,    0f, 0f, 1f, 0f, 1f
        )

        val indices = shortArrayOf(
            0, 1, 2
        )

        val mesh = Mesh(
            true,
            3,
            3,
            VertexAttributes(
                VertexAttribute.Position(),
                VertexAttribute.Normal(),
                VertexAttribute.TexCoords(0)
            )
        )

        mesh.setVertices(vertices)
        mesh.setIndices(indices)

        builder.begin()
        builder.part("triangleWall", mesh, GL20.GL_TRIANGLES, Material(texture))
        model = builder.end()
    }

    fun dispose() {
        model.dispose()
    }
}
