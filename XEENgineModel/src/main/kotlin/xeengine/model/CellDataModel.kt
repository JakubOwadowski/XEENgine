package xeengine.model

data class CellDataModel(
    val roof: RoofDataModel,
    val floor: FloorDataModel,
    val walls: WallsDataModel,
    val ambientObjects: List<AmbientObjectModel>,
    val map3dObjectModel: Map3dObjectModel
)
