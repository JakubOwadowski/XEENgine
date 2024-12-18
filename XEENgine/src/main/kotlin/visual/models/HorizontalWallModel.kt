package visual.models

import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute
import constants.CommonConstants.CELL_SIZE
import constants.CommonConstants.WALL_HEIGHT
import constants.CommonConstants.WALL_WIDTH
import globals.Globals

class HorizontalWallModel(texture: TextureAttribute) {
    val model = Globals.get().modelBuilder.createBox(
        CELL_SIZE + 2 * WALL_WIDTH, WALL_HEIGHT, WALL_WIDTH,
        Material(texture),
        (Usage.Position or Usage.Normal or Usage.TextureCoordinates).toLong()
    )

    fun dispose() {
        model.dispose()
    }
}