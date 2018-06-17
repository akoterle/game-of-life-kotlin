import java.util.*

typealias IntMatrix = MutableList<MutableList<Int>>

open class GolGrid(private val rows: Int,
                   private val cols: Int,
                   initialState: List<Int> = listOf()) {

    private var grid: IntMatrix = mutableListOf()

    init {
        val fillState = when (initialState.size == rows * cols) {
            true -> initialState
            else -> with(Random()) {
                (1..rows * cols).map { nextInt(2) }
            }
        }
        createGrid()
        fillGridUp(fillState)
    }

    fun nextGeneration(): List<List<Int>> {
        (0 until (rows * cols))
                .map { Pair(it / cols % rows, it % cols) }
                .map { applyRules(it.first, it.second) }
                .run(::fillGridUp)

        return grid.map(MutableList<Int>::toList)

    }


    private fun applyRules(row: Int, col: Int): Int {
        val (aliveNeighbors, _) = neighbors(row, col)
                .map { grid[it.first][it.second] }
                .partition { state -> state == 1 }

        val alive = grid[row][col] == 1

        return when (alive) {
            true -> when (aliveNeighbors.count()) {
                0, 1 -> 0
                2, 3 -> 1
                else -> 0
            }
            else -> when (aliveNeighbors.count()) {
                3 -> 1
                else -> 0
            }
        }
    }

    private fun createGrid() = (1..rows * cols)
            .map { Pair(it / cols % rows, it % cols) }
            .groupBy({ it.first }, { it.second })
            .forEach { grid.add(it.key, it.value.toMutableList()) }

    private fun fillGridUp(state: List<Int>) =
            state.mapIndexed { index, i ->
                val row = index / cols % rows
                val col = index % cols
                grid[row][col] = i
            }


    private fun neighbors(x: Int, y: Int): List<Pair<Int, Int>> {

        val neighbors: MutableList<Pair<Int, Int>> = mutableListOf()

        if (y + 1 < cols) neighbors.add(Pair(x, y + 1))
        if (y > 0) neighbors.add(Pair(x, y - 1))
        if (x + 1 < rows) neighbors.add(Pair(x + 1, y))
        if (x > 0) neighbors.add(Pair(x - 1, y))
        if ((x + 1 < rows) and (y + 1 < cols)) neighbors.add(Pair(x + 1, y + 1))
        if ((x + 1 < rows) and (y > 0)) neighbors.add(Pair(x + 1, y - 1))
        if ((x > 0) and (y + 1 < cols)) neighbors.add(Pair(x - 1, y + 1))
        if ((x > 0) and (y > 0)) neighbors.add(Pair(x - 1, y - 1))

        return neighbors

    }

}
