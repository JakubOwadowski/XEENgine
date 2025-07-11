package xeengine.model

data class MapParserConfigDataModel(
    val name: String,
    val path: String,
    val skybox: String,
    val mappings: Map<String, CellDataModel>
)
