package utils

fun interpolate(start: Float, end: Float, alpha: Float): Float {
    return start + (end - start) * alpha
}
