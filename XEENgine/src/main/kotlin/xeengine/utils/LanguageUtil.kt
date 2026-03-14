package xeengine.utils

import xeengine.common.cache.Cache

class LanguageUtil {
    companion object {
        fun get(key: String): String {
            var localisationValue = Cache.get().localisation[key]
            if (localisationValue == null) {
                localisationValue = "Missing localisation!"
            }
            return localisationValue
        }
    }
}