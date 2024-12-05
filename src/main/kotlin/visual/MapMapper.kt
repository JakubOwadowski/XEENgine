package visual

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun mapMap(path: String): Map {
    val image: BufferedImage = ImageIO.read(File(path))

    val width = image.width
    val height = image.height

    val map = Map(width, height)

    for (y in 0 until height) {
        for (x in 0 until width) {
            val argb = image.getRGB(width - 1 - x, height - 1 - y)
            val r = (argb shr 16) and 0xFF
            val g = (argb shr 8) and 0xFF
            val b = argb and 0xFF

            if (r == 0 && g == 0 && b == 0) {
                map.setCell(x, y, Cell(true, arrayOf(true, true, true, true), false))
            }
            if (r == 255 && g == 0 && b == 0) {
                map.setCell(x, y, Cell(true, arrayOf(false, false, false, false), true))
            }
        }
    }


    return map
}