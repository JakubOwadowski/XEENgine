package xeengine.src.main.utils

import xeengine.src.main.common.globals.Globals

class LanguageUtil {
    companion object {
        fun get(key: String): String {
            return Globals.get().localisation[key].toString()
        }
    }
}