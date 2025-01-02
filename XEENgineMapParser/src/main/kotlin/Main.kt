package xeengine.mapparser.src.main

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val gsonParser = GsonBuilder().setPrettyPrinting().create()
    val type = object : TypeToken<Map<String, Any>>() {}.type
    val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream("config/Config.json")
    val config: Map<String, Any> = gsonParser.fromJson(inputStream!!.reader(), type)
    val image: BufferedImage = ImageIO.read(File("XEENgineMapParser/src/main/resources/" + config["path"].toString()))
    val height = image.height
    val width = image.width
    val skybox = config["skybox"].toString()
    val mappings = config["mappings"] as Map<*, *>
    var output = mutableMapOf<String, Map<*, *>>()

    for (z in 0 until height) {
        for (x in 0 until width) {
            val argb = image.getRGB(width - 1 - x, height - 1 - z)
            val r = (argb shr 16) and 0xFF
            val g = (argb shr 8) and 0xFF
            val b = argb and 0xFF
            val rgb = "$r, $g, $b"
            output["$z, $x"] = mappings[rgb] as Map<*, *>
        }
    }

    val outputJSON = gsonParser.toJson(output)
    File("${config["name"]}.json").writeText(outputJSON)
}