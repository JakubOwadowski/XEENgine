package utils

import com.badlogic.gdx.math.Vector3

class Direction {
    private var vector = Vector3()

    constructor(vector: Vector3) {
        this.vector = vector
    }

    fun get(): Vector3 {
      return this.vector.cpy()
    }
}