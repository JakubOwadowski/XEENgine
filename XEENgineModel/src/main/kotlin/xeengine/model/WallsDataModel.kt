package xeengine.model

data class WallsDataModel(
    val north: WallDataModel,
    val south: WallDataModel,
    val east: WallDataModel,
    val west: WallDataModel
)