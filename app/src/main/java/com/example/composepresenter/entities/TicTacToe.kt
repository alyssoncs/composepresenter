package com.example.composepresenter.entities

class TicTacToe private constructor() {
    companion object {
        fun newGame(): TicTacToe = TicTacToe()
    }

    object InvalidMoveException: Throwable()

    enum class Play {
        PlayerOne,
        PlayerTwo,
        Empty,
    }

    enum class Result {
        InProgress,
        PlayerOneWon,
        PlayerTwoWon,
        Tie,
    }

    private var isPlayerOneTurn: Boolean = true

    private val board = Array(3) { Array(3) { Play.Empty } }

    fun play(x: Int, y: Int) {
        if (inputAreValid(x, y))
            doPlay(x, y)
        else
            throw InvalidMoveException
    }

    fun getBoard(): Array<Array<Play>> {
        return board
    }

    private fun inputAreValid(x: Int, y: Int): Boolean {
        return areInValidRange(x, y) && board[x][y].isEmpty()
    }

    private fun areInValidRange(x: Int, y: Int): Boolean {
        val verticalRange = board.indices
        val horizontalRange = board[0].indices

        return x in verticalRange && y in horizontalRange
    }

    private fun Play.isEmpty() = this == Play.Empty

    private fun doPlay(x: Int, y: Int) {
        board[x][y] = if (isPlayerOneTurn) Play.PlayerOne else Play.PlayerTwo

        isPlayerOneTurn = isPlayerOneTurn.not()
    }

    fun getResult(): Result {

        whoIsTheWinner()?.let {
            return if (it == Play.PlayerOne)
                Result.PlayerOneWon
            else
                Result.PlayerTwoWon
        }

        return if (isTie())
            Result.Tie
        else
            Result.InProgress
    }

    private fun isTie(): Boolean {
        for (row in board)
            for (play in row)
                if (play.isEmpty())
                    return false

        return true
    }

    private fun whoIsTheWinner(): Play? {
        whoIsTheWinnerInHorizontal()?.let { return it }
        hasWinnerInVertical()?.let { return it }
        whoIsTheWinnerInDiagonal()?.let { return it }
        return null
    }

    private fun whoIsTheWinnerInHorizontal(): Play? {
        for (i in board.indices)
            whoIsTheWinnerInRow(i)?.let { return it }

        return null
    }

    private fun hasWinnerInVertical(): Play? {
        for (j in board[0].indices)
            whoIsTheWinnerInColumn(j)?.let { return it }

        return null
    }

    private fun whoIsTheWinnerInDiagonal(): Play? {
        whoIsTheWinnerInMainDiagonal()?.let { return it }
        whoIsTheWinnerInAntiDiagonal()?.let { return it }

        return null
    }

    private fun whoIsTheWinnerInRow(i: Int): Play? {
        val row = board[i].asList()
        return whoIsTheWinnerIn(row)
    }

    private fun whoIsTheWinnerInColumn(j: Int): Play? {
        val column = mutableListOf<Play>()
        for (i in board.indices)
            column.add(board[i][j])

        return whoIsTheWinnerIn(column)
    }

    private fun whoIsTheWinnerInMainDiagonal(): Play? {
        val mainDiagonal = mutableListOf<Play>()
        for (i in board.indices)
            mainDiagonal.add(board[i][i])

        return whoIsTheWinnerIn(mainDiagonal)
    }

    private fun whoIsTheWinnerInAntiDiagonal(): Play? {
        val antiDiagonal = mutableListOf<Play>()
        for (i in board.indices)
            antiDiagonal.add(board[i][board.lastIndex - i])

        return whoIsTheWinnerIn(antiDiagonal)
    }

    private fun whoIsTheWinnerIn(plays: List<Play>): Play? {
        if (plays[0].isEmpty())
            return null

        for (index in 0 until plays.lastIndex)
            if (plays[index] != plays[index + 1])
                return null

        return plays.first()
    }
}
