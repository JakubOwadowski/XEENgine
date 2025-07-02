package xeengine.src.main.globals

import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import constants.MapDimensionsConstants
import maps.VERTIGO_CITY_MAP
import xeengine.src.main.common.settings.settings.Settings
import xeengine.src.main.logger.Logger
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class Globals private constructor() {
    val modelBuilder = ModelBuilder()
    val map = VERTIGO_CITY_MAP
    val version = getVersionFromPom()
    lateinit var localisation: Map<String, String>

    companion object {
        @Volatile private var instance: Globals? = null

        fun get(): Globals =
            instance ?: synchronized(this) {
                instance ?: Globals().also { instance = it }
            }
    }

    init {
        Logger.initLog("Initialising globals", Globals::class)
        getLocalisation()
    }

    private fun getLocalisation() {
        Logger.initLog("Loading localisation", Globals::class)
        val gsonParser = Gson()
        val type = object : TypeToken<Map<String, String>>() {}.type

        val language = Settings.language

        val inputStream = this::class.java.getResourceAsStream("/localisation/localisation-$language.json")
        Logger.initLog("Localisation language: $language", Globals::class)
        localisation = gsonParser.fromJson(inputStream!!.reader(), type)
    }

    private fun getVersionFromPom(): String {
        val pomFile = File("XEENgine/pom.xml")

        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val document = documentBuilder.parse(pomFile)
        val versionNodes = document.getElementsByTagName("version")

        return versionNodes.item(0).textContent
    }
}