package com.example.composepresenter.usecases.tictactoe

data class TicTacToeResult(
    val hasWinner: Boolean,
    val isTie: Boolean,
    val winner: Int,
    val history: List<Pair<Int, Int>>
) {

    fun getBoard(): List<List<TicTacToePlay>> {
        val board = createEmptyBoard()

        history.forEachIndexed { i, pair ->
            board[pair.first][pair.second] = if (i % 2 == 0)
                TicTacToePlay.PlayerOne
            else
                TicTacToePlay.PlayerTwo
        }

        return board
    }

    fun newPlay(x: Int, y: Int): TicTacToeResult {
        return this.copy(
            history = history.plus(Pair(x, y))
        )
    }

    private fun createEmptyBoard() = List(3) { MutableList(3) { TicTacToePlay.Empty } }
}
