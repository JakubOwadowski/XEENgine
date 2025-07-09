package xeengine.common.settings.enums

import xeengine.common.constants.global.GlobalSettingsConstants.SETTING_ENGLISH_LANGUAGE
import xeengine.common.constants.global.GlobalSettingsConstants.SETTING_FRENCH_LANGUAGE

enum class SettingLanguageEnum(val language: String) {
    ENGLISH(SETTING_ENGLISH_LANGUAGE),
    FRENCH(SETTING_FRENCH_LANGUAGE);
    override fun toString(): String = language
}