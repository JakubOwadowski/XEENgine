package globals

import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import maps.VertigoCityMap

class Globals private constructor() {
    val modelBuilder = ModelBuilder()
    val map = VertigoCityMap

    companion object {
        @Volatile private var instance: Globals? = null

        fun get(): Globals =
            instance ?: synchronized(this) {
                instance ?: Globals().also { instance = it }
            }
    }
}