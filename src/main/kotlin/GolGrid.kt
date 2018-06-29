import java.util.*

typealias IntMatrix = MutableList<MutableList<Int>>

open class GolGrid private constructor(
                   private val rows: Int,
                   private val cols: Int,
                   initialState: List<Int>) {

    private var grid: IntMatrix = MutableList(rows,{ MutableList(cols){ 0 }})

    init {
        fillGridUp(initialState)
    }

    fun nextGeneration(): List<List<Int>> {
        (0 until rows).flatMap { row ->
            (0 until cols).map { col ->
                Triple(row,col,applyRules(row,col))
            }
        }.forEach{ (row,col,value) -> grid[row][col] = value}

        return grid.map(MutableList<Int>::toList)

    }
    
    private fun applyRules(row: Int, col: Int): Int {
        val numOfAliveNeighbors = neighbours(row, col)
                .map { grid[it.first][it.second] }
                .filter { it == 1 }
                .count()

        val isAlive = grid[row][col] == 1

        return rules(isAlive,numOfAliveNeighbors)
    }

    private fun rules(alive:Boolean,numOfAliveNeighbors:Int) = when (numOfAliveNeighbors) {
        2 -> if(alive) 1 else 0
        3 -> 1
        else -> 0
    }

    private fun fillGridUp(state: List<Int>) =
            state.mapIndexed { index, i ->
                val row = index / cols % rows
                val col = index % cols
                grid[row][col] = i
            }

    private fun neighbours(x: Int, y: Int): List<Pair<Int, Int>> {
        return ((x - 1)..(x + 1)).filter { it in 0..(rows - 1) }.flatMap { px ->
            ((y - 1)..(y + 1)).filter { it in 0..(cols - 1) } .map { py ->
                (px to py)
            }
        }.filter { it.first != x || it.second != y }
    }

    companion object {

        operator fun invoke(rows: Int,cols: Int) = GolGrid(rows,cols,randomInitialState(rows,cols))
        operator fun invoke(rows: Int,cols: Int,initialState: List<Int>) =
                if (initialState.count() == (rows*cols))
                    GolGrid(rows,cols,initialState)
                else
                    GolGrid(rows,cols)

        private fun randomInitialState(rows: Int,cols: Int) = with(Random()) {
            (1..(rows * cols)).map { if (nextBoolean()) 1 else 0 }
        }
    }

}
