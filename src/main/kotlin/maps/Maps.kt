package maps

import visual.Map

import visual.mapMap
import xeengine.src.main.utils.Coordinates

val VertigoCityMap: Map = mapMap(arrayOf(
    "src/main/resources/maps/Vertigo.png",
//    "src/main/resources/maps/Vertigo2.png"
),
    32,
    32,
    1).apply { this.initCoordinates = Coordinates(16, 0) }
