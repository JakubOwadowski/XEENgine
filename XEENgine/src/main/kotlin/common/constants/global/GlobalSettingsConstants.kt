package xeengine.src.main.common.constants.global

import common.settings.enums.CameraEnum.FLUID
import xeengine.src.main.common.settings.common.Settings

object GlobalSettingsConstants {
    val SETTING_ALLOW_INPUT_CHAINING: Boolean = if (Settings.camera == FLUID) false else true
}