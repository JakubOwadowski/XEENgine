package xeengine.visual.primitives

data class Map3dObject (
    var north: Map3dWall,
    var west: Map3dWall,
    var east: Map3dWall,
    var south: Map3dWall
)