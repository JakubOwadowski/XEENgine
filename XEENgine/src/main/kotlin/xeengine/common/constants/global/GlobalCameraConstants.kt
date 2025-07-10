package xeengine.common.constants.global

import xeengine.common.settings.enums.SettingCameraEnum.FLUID
import xeengine.common.settings.common.Settings

object GlobalCameraConstants {
    const val CAMERA_FOW: Float = 80f
    const val CAMERA_NEAR: Float = 0.1f
    val CAMERA_FAR: Float = if (Settings.camera == FLUID) 100f else 20f
    const val CAMERA_HEIGHT: Float = 0.5f
    val CAMERA_MOVING_TIME = if (Settings.camera == FLUID) 0.15f else 0f
    const val CAMERA_FLOOR_LEVEL: Float = 0f
    const val CAMERA_STEP_SIZE: Float = 2f //one cell
    val CAMERA_BOBBING_TIME: Float = if (Settings.camera == FLUID) 2f else 0f
    val CAMERA_BOBBING_HEIGHT: Float = if (Settings.camera == FLUID) 0.03f else 0f
}