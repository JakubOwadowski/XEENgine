package visual

import xeengine.src.main.visual.textures.TEXTURE_WALL
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun mapMap(name: String, paths: Array<String>, width: Int, height: Int, levels: Int): Map {

    val map = Map(name, width, height, levels)

    for (y in 0 until levels) {
        val image: BufferedImage = ImageIO.read(File(paths[y]))
        for (z in 0 until height) {
            for (x in 0 until width) {
                val argb = image.getRGB(width - 1 - x, height - 1 - z)
                val r = (argb shr 16) and 0xFF
                val g = (argb shr 8) and 0xFF
                val b = argb and 0xFF

                if (r == 0 && g == 0 && b == 0) {
                    map.setCell(x, y, z, Cell(true, true, arrayOf(Wall(true, TEXTURE_WALL), Wall(true, TEXTURE_WALL), Wall(true, TEXTURE_WALL), Wall(true, TEXTURE_WALL)), false))
                }
                if (r == 255 && g == 0 && b == 0) {
                    map.setCell(x, y, z, Cell(true, true, arrayOf(Wall(false), Wall(false), Wall(false), Wall(false)), true))
                }
                if (r == 0 && g == 255 && b == 255) {
                    map.setCell(x, y, z, Cell(false, false, arrayOf(Wall(false), Wall(false), Wall(false), Wall(false)), true))
                }
                if (r == 48 && g == 48 && b == 48) {
                    map.setCell(x, y, z, Cell(false, false, arrayOf(Wall(true), Wall(true), Wall(true), Wall(true)), true))
                }
            }
        }
    }

    return map
}