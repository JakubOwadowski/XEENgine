package xeengine.src.main.common.settings.settings

import xeengine.src.main.common.settings.common.Setting
import xeengine.src.main.common.settings.enums.SettingLanguageEnum

class Settings {
    companion object {
        private var _language = Setting(SettingLanguageEnum.entries, SettingLanguageEnum.ENGLISH)

        var language: SettingLanguageEnum
            get() = _language.value
            set(value) {
                _language.value = value
            }
    }
}
