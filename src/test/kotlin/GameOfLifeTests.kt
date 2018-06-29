import org.junit.Assert
import org.junit.Test

class GameOfLifeTests {

    @Test
    fun `a live cell with zero or one live neighbours will die`() {

        GolGrid(3, 3, listOf(
                0, 0, 0,
                0, 1, 0,
                0, 0, 0)) //given
                .run { nextGeneration() } //when
                .let { state -> Assert.assertEquals(0, state[1][1]) } //then

        GolGrid(3, 3, listOf(
                1, 0, 0,
                0, 1, 0,
                0, 0, 0))
                .run { nextGeneration() }
                .let { state -> Assert.assertEquals(0, state[1][1]) }

        GolGrid(3, 3, listOf(
                0, 0, 0,
                0, 1, 0,
                0, 0, 1))
                .run { nextGeneration() }
                .let { state -> Assert.assertEquals(0, state[1][1]) }

        GolGrid(3, 3, listOf(
                0, 0, 0,
                0, 1, 0,
                1, 0, 0))
                .run { nextGeneration() }
                .let { state -> Assert.assertEquals(0, state[1][1]) }
    }

    @Test
    fun `a live cell with two or three live neighbours will remain alive`() {
        GolGrid(3, 3, listOf(
                1, 1, 0,
                0, 1, 0,
                0, 0, 0))
                .run { nextGeneration() }
                .let { state -> Assert.assertEquals(1, state[1][1]) }
        GolGrid(3, 3, listOf(
                1, 1, 0,
                1, 1, 0,
                0, 0, 0))
                .run { nextGeneration() }
                .let { state -> Assert.assertEquals(1, state[1][1]) }


        GolGrid(3, 3, listOf(
                0, 1, 0,
                1, 1, 0,
                0, 1, 0)).run { nextGeneration() }
                .let { state -> Assert.assertEquals(1, state[1][1]) }
    }

    @Test
    fun `a live cell with four or more live neighbours will die`() {
        GolGrid(3, 3, listOf(
                1, 1, 1,
                1, 1, 0,
                0, 0, 0))
                .run { nextGeneration() }
                .let { state -> Assert.assertEquals(0, state[1][1]) }

        GolGrid(3, 3, listOf(
                1, 1, 1,
                1, 1, 1,
                0, 0, 0))
                .run { nextGeneration() }
                .let { state -> Assert.assertEquals(0, state[1][1]) }
        GolGrid(3, 3, listOf(
                1, 1, 1,
                1, 1, 1,
                1, 1, 1))
                .run { nextGeneration() }
                .let { state -> Assert.assertEquals(0, state[1][1]) }
    }

    @Test
    fun `a dead cell with exactly three live neighbours becomes alive`() {
        GolGrid(3, 3, listOf(
                0, 1, 1,
                0, 0, 1,
                0, 0, 0))
                .run { nextGeneration() }
                .let { state -> Assert.assertEquals(1, state[1][1]) }
        GolGrid(3, 3, listOf(
                0, 1, 0,
                1, 0, 0,
                0, 1, 0))
                .run { nextGeneration() }
                .let { state -> Assert.assertEquals(1, state[1][1]) }
    }

    @Test
    fun `in all other cases a dead cell will stay dead`() {
        GolGrid(3, 3, listOf(
                0, 1, 1,
                0, 0, 0,
                0, 0, 0))
                .run { nextGeneration() }
                .let { state -> Assert.assertEquals(0, state[1][1]) }
        GolGrid(3, 3, listOf(
                1, 0, 1,
                0, 0, 0,
                0, 0, 0))
                .run { nextGeneration() }
                .let { state -> Assert.assertEquals(0, state[1][1]) }
        GolGrid(3, 3, listOf(
                0, 0, 0,
                0, 0, 0,
                0, 1, 1))
                .run { nextGeneration() }
                .let { state -> Assert.assertEquals(0, state[1][1]) }

    }

    @Test
    fun `should work on not square grids`() {
        GolGrid(3, 4, listOf(
                0, 0, 0, 1,
                0, 0, 0, 1,
                0, 0, 0, 1))
                .run { nextGeneration() }
                .let { state -> Assert.assertEquals(1, state[1][2]) }
    }
}