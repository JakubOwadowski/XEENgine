package xeengine.map.parser.runner

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import xeengine.model.CellDataModel
import xeengine.model.MapParserConfigDataModel
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.collections.get

fun main() {
    val resourceRoot = "XEENgineMapParser/src/main/resources"

    val gsonParser = GsonBuilder().setPrettyPrinting().create()
    val config: MapParserConfigDataModel = gsonParser.fromJson(
        File("$resourceRoot/config/Config.json").reader(),
        MapParserConfigDataModel::class.java
    )
    val image: BufferedImage = ImageIO.read(File("$resourceRoot/${config.path}"))

    val height = image.height
    val width = image.width
    val skybox = config.skybox
    val mappings = config.mappings
    var output = mutableMapOf<String, CellDataModel>()

    for (z in 0 until height) {
        for (x in 0 until width) {
            val argb = image.getRGB(width - 1 - x, height - 1 - z)
            val rgb = getRGB(argb)
            output["$z, $x"] = mappings[rgb] as CellDataModel
        }
    }

    val outputJSON = gsonParser.toJson(output)
    File("${config.name}.json").writeText(outputJSON)
}

fun getRGB(argb: Int): String {
    val r = (argb shr 16) and 0xFF
    val g = (argb shr 8) and 0xFF
    val b = argb and 0xFF
    return "$r, $g, $b"
}