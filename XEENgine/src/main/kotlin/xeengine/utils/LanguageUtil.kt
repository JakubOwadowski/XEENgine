package xeengine.utils

import xeengine.common.globals.Globals

class LanguageUtil {
    companion object {
        fun get(key: String): String {
            var localisationValue = Globals.get().localisation[key]
            if (localisationValue == null) {
                localisationValue = "Missing localisation!"
            }
            return localisationValue
        }
    }
}