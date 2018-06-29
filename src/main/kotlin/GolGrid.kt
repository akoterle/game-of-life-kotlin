import java.util.*

class GolGrid private constructor(
                   private val rows: Int,
                   private val cols: Int,
                   initialState: List<Int>) {

    private val grid = MutableList(rows,{ MutableList(cols){ DEAD }})

    init {
        initialState.mapIndexed { index, aliveValue ->
            val row = index / cols % rows
            val col = index % cols
            grid[row][col] = aliveValue
        }
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
                .filter { it == ALIVE }
                .count()

        val isAlive = grid[row][col] == ALIVE

        return rules(isAlive,numOfAliveNeighbors)
    }

    private fun rules(alive:Boolean,numOfAliveNeighbors:Int) = when (numOfAliveNeighbors) {
        2 -> if(alive) ALIVE else DEAD
        3 -> ALIVE
        else -> DEAD
    }

    private fun neighbours(x: Int, y: Int): List<Pair<Int, Int>> {
        return ((x - 1)..(x + 1)).filter { it in 0..(rows - 1) }.flatMap { px ->
            ((y - 1)..(y + 1)).filter { it in 0..(cols - 1) } .map { py ->
                (px to py)
            }
        }.filter { it.first != x || it.second != y }
    }

    companion object {
        const val ALIVE = 1
        const val DEAD = 0

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
