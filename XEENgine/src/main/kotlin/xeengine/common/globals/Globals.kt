package xeengine.common.globals

import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import xeengine.maps.common.VERTIGO_CITY_MAP
import xeengine.common.settings.common.Settings
import xeengine.common.logger.Logger
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.xml.parsers.DocumentBuilderFactory

class Globals private constructor() {
    val modelBuilder = ModelBuilder()
    val map = VERTIGO_CITY_MAP
    val version = getVersionFromPom()
    val yamlMapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
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
        val language = Settings.language
        val localisationDir = Paths.get("XEENgine/src/main/resources/localisation")
        val localisationMap = mutableMapOf<String, String>()

        Files.walk(localisationDir)
            .filter { path ->
                Files.isRegularFile(path) &&
                        path.fileName.toString().endsWith("-$language.yml")
            }
            .forEach { path ->
                Logger.initLog("Reading localisation file: ${path.fileName}", Globals::class)

                @Suppress("unchecked_cast")
                val map = yamlMapper.readValue(
                    path.toFile(),
                    Map::class.java
                ) as Map<String, String>

                localisationMap.putAll(map)
            }

        localisation = localisationMap
    }

    private fun getVersionFromPom(): String {
        val pomFile = File("XEENgine/pom.xml")

        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val document = documentBuilder.parse(pomFile)
        val versionNodes = document.getElementsByTagName("version")

        return versionNodes.item(0).textContent
    }
}