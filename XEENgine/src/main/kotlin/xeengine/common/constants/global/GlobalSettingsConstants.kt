package xeengine.common.constants.global

import xeengine.common.settings.enums.SettingCameraEnum.FLUID
import xeengine.common.settings.common.Settings

object GlobalSettingsConstants {
    val SETTING_ALLOW_INPUT_CHAINING: Boolean = if (Settings.camera == FLUID) false else true
    const val SETTING_ENGLISH_LANGUAGE: String = "EN"
    const val SETTING_FRENCH_LANGUAGE: String = "FR"
}