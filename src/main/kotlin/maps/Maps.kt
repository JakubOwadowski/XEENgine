package maps

import visual.Map
import visual.mapMap
import xeengine.src.main.utils.Coordinates

val VertigoCityMap: Map = mapMap("src/main/resources/maps/Vertigo.png").apply { this.initCoordinates = Coordinates(16, 0) }
