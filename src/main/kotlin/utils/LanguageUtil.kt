package xeengine.src.main.utils

import constants.CommonConstants
import globals.Globals

class LanguageUtil {
    companion object {
        fun get(key: String): String {
            return Globals.get().localisation[key].toString()
        }
    }
}