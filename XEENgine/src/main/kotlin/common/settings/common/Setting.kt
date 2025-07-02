package xeengine.src.main.common.settings.common

class Setting<T>(
    private val allowedValues: List<T>,
    default: T
) {
    var value: T = default
}
