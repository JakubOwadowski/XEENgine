package globals

import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import constants.CommonConstants
import maps.VertigoCityMap

class Globals private constructor() {
    val modelBuilder = ModelBuilder()
    val map = VertigoCityMap
    val version = "v0.0.1 (alpha)"
    lateinit var localisation: Map<String, String>

    companion object {
        @Volatile private var instance: Globals? = null

        fun get(): Globals =
            instance ?: synchronized(this) {
                instance ?: Globals().also { instance = it }
            }
    }

    init {
        getLocalisation()
    }

    private fun getLocalisation() {
        val gsonParser = Gson()
        val type = object : TypeToken<Map<String, String>>() {}.type
        val inputStream = this::class.java.getResourceAsStream("/localisation/localisation-${CommonConstants.LANGUAGE}.json")
        localisation = gsonParser.fromJson(inputStream!!.reader(), type)
    }
}