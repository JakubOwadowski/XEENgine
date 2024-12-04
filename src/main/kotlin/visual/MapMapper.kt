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
            val argb = image.getRGB(x, y)
            val alpha = (argb shr 24) and 0xFF
            val red = (argb shr 16) and 0xFF
            val green = (argb shr 8) and 0xFF
            val blue = argb and 0xFF

            // Check if the pixel is pure black
            if (red == 0 && green == 0 && blue == 0) {
                map.setCell(x, y, Cell(true, arrayOf(true, true, true, true)))
            }
            // Check if the pixel is pure black
            if (red == 255 && green == 0 && blue == 0) {
                map.setCell(x, y, Cell(true, arrayOf(false, false, false, false)))
            }
        }
    }

    return map
}