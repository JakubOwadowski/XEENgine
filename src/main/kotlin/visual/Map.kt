package visual

import xeengine.src.main.utils.Coordinates

class Map(val name: String, width: Int, height: Int, levels: Int) {
    private var map: Array<Array<Array<Cell>>> = Array(levels) { Array(width) { Array(height) { Cell() } } }
    var initCoordinates = Coordinates(0, 0)

    fun getCell(x: Int, y: Int, z: Int): Cell {
        return map[y][x][z]
    }

    fun setCell(x: Int, y: Int, z: Int, cell: Cell) {
        map[y][x][z] = cell
    }

    fun width(): Int {
        return map[0][0].size
    }

    fun height(): Int {
        return map[0].size
    }

    fun getLevelIndices(): IntRange {
        return map.indices
    }

    fun getColumnIndices(y: Int): IntRange {
        return map[y].indices
    }

    fun getRowIndices(x: Int, y: Int): IntRange {
        return map[y][x].indices
    }

    fun dispose() {
        for (y in map.indices) {
            for (x in map[y].indices) {
                for (z in map[y][x].indices) {
                    val cell = map[y][x][z]
                    cell.dispose()
                }
            }
        }
    }
}