package xeengine.src.main.common.constants.global

import com.badlogic.gdx.math.Vector3
import utils.Direction

object GlobalDirectionsConstants {
    val DIRECTION_NORTH: Direction = Direction(Vector3(0f, 0f, 1f))
    val DIRECTION_SOUTH: Direction = Direction(Vector3(0f, 0f, -1f))
    val DIRECTION_EAST: Direction = Direction(Vector3(1f, 0f, 0f))
    val DIRECTION_WEST: Direction = Direction(Vector3(-1f, 0f, 0f))
}