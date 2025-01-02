package maps

import visual.Map

import visual.mapMap
import xeengine.src.main.utils.Coordinates

val VertigoCityMap: Map = mapMap("Vertigo", arrayOf(
    "XEENgine/src/main/resources/maps/Vertigo.json",
),
    32,
    32,
    1,
    "XEENgine/src/main/resources/misc/skybox.png").apply { this.initCoordinates = Coordinates(16, 0) }
