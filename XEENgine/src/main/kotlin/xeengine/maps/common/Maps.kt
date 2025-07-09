package xeengine.maps.common

import xeengine.visual.primitives.Map
import xeengine.utils.MapLoader
import xeengine.utils.Coordinates

val VERTIGO_CITY_MAP: Map = MapLoader.loadMap(
    "Vertigo",
    arrayOf("XEENgine/src/main/resources/maps/Vertigo.json"),
    32,
    32,
    1,
    "XEENgine/src/main/resources/misc/skybox.png").apply { this.initCoordinates = Coordinates(16, 0) }
