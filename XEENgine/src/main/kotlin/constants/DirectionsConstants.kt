package constants

import com.badlogic.gdx.math.Vector3
import utils.Direction

object DirectionsConstants {
    @JvmStatic
    val NORTH = Direction(Vector3(0f, 0f, 1f))
    @JvmStatic
    val SOUTH = Direction(Vector3(0f, 0f, -1f))
    @JvmStatic
    val EAST = Direction(Vector3(1f, 0f, 0f))
    @JvmStatic
    val WEST = Direction(Vector3(-1f, 0f, 0f))
}