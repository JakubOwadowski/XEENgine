package xeengine.utils

import com.badlogic.gdx.math.Vector3

class Direction(private var vector: Vector3) {
    fun get(): Vector3 {
      return this.vector.cpy()
    }
}