package xeengine.maps.common

import xeengine.visual.primitives.GameMap
import xeengine.utils.MapLoader
import xeengine.utils.Coordinates
import xeengine.common.constants.global.Paths

val VERTIGO_CITY_MAP: GameMap by lazy {
    MapLoader.loadMap(
        "Vertigo",
        arrayOf(Paths.MAP_VERTIGO),
        32,
        32,
        1,
        Paths.SKYBOX
    ).apply { initCoordinates = Coordinates(16, 0) }
}