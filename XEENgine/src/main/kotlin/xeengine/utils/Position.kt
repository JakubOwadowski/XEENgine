package xeengine.utils

import com.badlogic.gdx.math.Vector3

class Position : Vector3 {
    constructor(x: Float, y: Float, z: Float) : super(x, y, z)
    constructor(vector3: Vector3) : super(vector3)
    constructor() : super()
}