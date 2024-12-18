package globals

import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import constants.CommonConstants
import maps.VertigoCityMap
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class Globals private constructor() {
    val modelBuilder = ModelBuilder()
    val map = VertigoCityMap
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
        getLocalisation()
    }

    private fun getLocalisation() {
        val gsonParser = Gson()
        val type = object : TypeToken<Map<String, String>>() {}.type
        val inputStream = this::class.java.getResourceAsStream("/localisation/localisation-${CommonConstants.LANGUAGE}.json")
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