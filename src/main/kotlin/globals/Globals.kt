package globals

import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder

class Globals private constructor() {
    val modelBuilder = ModelBuilder()

    companion object {
        @Volatile private var instance: Globals? = null

        fun get(): Globals =
            instance ?: synchronized(this) {
                instance ?: Globals().also { instance = it }
            }
    }
}