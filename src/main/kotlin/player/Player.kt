package player

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PerspectiveCamera
import constants.CommonConstants
import constants.DirectionsConstants
import utils.Position

class Player {
    val position = Position()
    val facing = DirectionsConstants.NORTH.get()
    var camera: PerspectiveCamera

    constructor(cameraWidth: Float, cameraHeight: Float) {
        position.x = 0f
        position.y = 0.5f
        position.z = 0f
        camera = PerspectiveCamera(CommonConstants.PLAYER_FOW, cameraWidth, cameraHeight)
        camera.position.set(position.x, position.y, position.z)
        camera.lookAt(position.x + facing.x, position.y, position.z + facing.z)
        camera.near = CommonConstants.CAMERA_NEAR
        camera.far = CommonConstants.CAMERA_FAR
    }
}