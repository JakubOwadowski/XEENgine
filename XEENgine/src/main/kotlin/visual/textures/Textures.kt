package xeengine.src.main.visual.textures

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute

class Textures {
    companion object {
        private val textures = mapOf<String, TextureAttribute> (
            "TEXTURE_WALL" to getTextureAttribute("XEENgine/src/main/resources/sprites/walls/wall.png"),
            "TEXTURE_FLOOR" to getTextureAttribute("XEENgine/src/main/resources/sprites/floors/floor.png")
        )

        private fun getTextureAttribute(texturePath: String): TextureAttribute {
            return TextureAttribute.createDiffuse(Texture(Gdx.files.internal(texturePath)))
        }

        fun get(textureName: String): TextureAttribute {
            return textures[textureName]!!
        }
    }
}

