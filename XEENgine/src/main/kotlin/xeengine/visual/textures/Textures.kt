package xeengine.visual.textures

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute
import xeengine.common.constants.global.Paths
import xeengine.common.logger.Logger

class Textures {
    companion object {
        private val textures = mapOf(
            "TEXTURE_WALL"    to getTextureAttribute(Paths.TEXTURE_WALL),
            "TEXTURE_FLOOR"   to getTextureAttribute(Paths.TEXTURE_FLOOR),
            "TEXTURE_DOORWAY" to getTextureAttribute(Paths.TEXTURE_DOORWAY),
            "TEXTURE_TENT_FRONT" to getTextureAttribute(Paths.TEXTURE_TENT_FRONT),
            "TEXTURE_TENT_SIDE" to getTextureAttribute(Paths.TEXTURE_TENT_SIDE),
        )

        private val dummy: TextureAttribute by lazy {
            val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
            pixmap.setColor(Color.MAGENTA)
            pixmap.fill()
            val texture = Texture(pixmap)
            pixmap.dispose()
            TextureAttribute.createDiffuse(texture)
        }

        private fun getTextureAttribute(texturePath: String): TextureAttribute {
            return TextureAttribute.createDiffuse(Texture(Gdx.files.internal(texturePath)))
        }

        fun get(textureName: String): TextureAttribute {
            return textures[textureName] ?: run {
                Logger.gameLog("Texture not found: $textureName — using dummy", Textures::class)
                dummy
            }
        }
    }
}