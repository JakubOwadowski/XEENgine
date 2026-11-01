package xeengine.common.cache

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import xeengine.maps.common.VERTIGO_CITY_MAP
import xeengine.common.constants.global.Paths
import xeengine.common.settings.common.Settings
import xeengine.common.logger.Logger
import xeengine.visual.primitives.GameMap
import java.util.Properties

class Cache private constructor() {
    val modelBuilder = ModelBuilder()
    val map: GameMap by lazy { VERTIGO_CITY_MAP }
    val version = readVersion()
    private val yamlMapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
    lateinit var localisation: Map<String, String>

    companion object {
        @Volatile private var instance: Cache? = null

        fun get(): Cache =
            instance ?: synchronized(this) {
                instance ?: Cache().also { instance = it }
            }
    }

    init {
        Logger.initLog("Initialising cache", Cache::class)
        getLocalisation()
    }

    private fun getLocalisation() {
        Logger.initLog("Loading localisation", Cache::class)
        val language = Settings.language
        val localisationMap = mutableMapOf<String, String>()

        val localisationRoot = Gdx.files.internal(Paths.LOCALISATION_DIR)
        localisationRoot.list().forEach { langDir ->
            langDir.list().filter { it.name().endsWith("-$language.yml") }.forEach { file ->
                Logger.initLog("Reading localisation file: ${file.name()}", Cache::class)
                @Suppress("UNCHECKED_CAST")
                val map = yamlMapper.readValue(file.readString(), Map::class.java) as Map<String, String>
                localisationMap.putAll(map)
            }
        }

        localisation = localisationMap
    }

    private fun readVersion(): String {
        return try {
            val props = Properties()
            props.load(Gdx.files.internal(Paths.VERSION_PROPERTIES).read())
            props.getProperty("version", "unknown")
        } catch (e: Exception) {
            Logger.initLog("Could not read version.properties: ${e.message}", Cache::class)
            "unknown"
        }
    }
}