package visual

import xeengine.src.main.utils.Coordinates

class Map(width: Int, height: Int) {
    private var map: Array<Array<Cell>> = Array(width) { Array(height) { Cell() } }
    var initCoordinates = Coordinates(0, 0)

    fun getCell(x: Int, y: Int): Cell {
        return map[x][y]
    }

    fun setCell(x: Int, y: Int, cell: Cell) {
        map[x][y] = cell
    }

    fun width(): Int {
        return map[0].size
    }

    fun height(): Int {
        return map.size
    }

    fun getColumnIndices(): IntRange {
        return map.indices
    }

    fun getRowIndices(x: Int): IntRange {
        return map[x].indices
    }

    fun dispose() {
        for (x in map.indices) {
            for (y in map[x].indices) {
                val cell = map[x][y]
                cell.dispose()
            }
        }
    }
}