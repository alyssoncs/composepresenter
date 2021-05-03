package com.example.composepresenter.entities

class TicTacToe private constructor() {
    companion object {

        fun newGame(): TicTacToe = TicTacToe()

        private const val EMPTY = ' '

        private object PlayerOne {
            const val number = 1
            const val symbol = 'X'
        }

        private object PlayerTwo {
            const val number = 2
            const val symbol = 'O'
        }
    }

    object InvalidMoveException: Throwable()

    enum class Play {
        PlayerOne,
        PlayerTwo,
        Empty,
    }

    private var isPlayerOneTurn: Boolean = true

    private val board = Array(3) { Array(3) { EMPTY } }

    fun play(x: Int, y: Int) {
        if (inputAreValid(x, y))
            doPlay(x, y)
        else
            throw InvalidMoveException
    }

    fun getBoard(): Array<Array<Play>> {

        return board.map { row ->
            row.map { play ->
                when (play) {
                    PlayerOne.symbol -> Play.PlayerOne
                    PlayerTwo.symbol -> Play.PlayerTwo
                    else -> Play.Empty
                }
            }.toTypedArray()
        }.toTypedArray()
    }

    private fun inputAreValid(x: Int, y: Int): Boolean {
        return areValid(x, y) && board[x][y].isEmpty()
    }

    private fun areValid(x: Int, y: Int): Boolean {
        val verticalRange = board.indices
        val horizontalRange = board[0].indices

        return x in verticalRange && y in horizontalRange
    }

    private fun Char.isEmpty() = this == EMPTY

    private fun doPlay(x: Int, y: Int) {
        board[x][y] = if (isPlayerOneTurn) PlayerOne.symbol else PlayerTwo.symbol

        isPlayerOneTurn = isPlayerOneTurn.not()
    }

    fun getResult(): Result {

        whoIsTheWinner()?.let {
            return if (it == PlayerOne.number)
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

    private fun whoIsTheWinner(): Int? {
        whoIsTheWinnerInHorizontal()?.let { return it }
        hasWinnerInVertical()?.let { return it }
        whoIsTheWinnerInDiagonal()?.let { return it }
        return null
    }

    private fun whoIsTheWinnerInHorizontal(): Int? {
        for (i in board.indices)
            whoIsTheWinnerInRow(i)?.let { return it }

        return null
    }

    private fun hasWinnerInVertical(): Int? {
        for (j in board[0].indices)
            whoIsTheWinnerInColumn(j)?.let { return it }

        return null
    }

    private fun whoIsTheWinnerInDiagonal(): Int? {
        whoIsTheWinnerInMainDiagonal()?.let { return it }
        whoIsTheWinnerInAntiDiagonal()?.let { return it }

        return null
    }

    private fun whoIsTheWinnerInRow(i: Int): Int? {
        val row = board[i].asList()
        return whoIsTheWinnerIn(row)
    }

    private fun whoIsTheWinnerInColumn(j: Int): Int? {
        val column = mutableListOf<Char>()
        for (i in board.indices)
            column.add(board[i][j])

        return whoIsTheWinnerIn(column)
    }

    private fun whoIsTheWinnerInMainDiagonal(): Int? {
        val mainDiagonal = mutableListOf<Char>()
        for (i in board.indices)
            mainDiagonal.add(board[i][i])

        return whoIsTheWinnerIn(mainDiagonal)
    }

    private fun whoIsTheWinnerInAntiDiagonal(): Int? {
        val antiDiagonal = mutableListOf<Char>()
        for (i in board.indices)
            antiDiagonal.add(board[i][board.lastIndex - i])

        return whoIsTheWinnerIn(antiDiagonal)
    }

    private fun whoIsTheWinnerIn(plays: List<Char>): Int? {
        if (plays[0].isEmpty())
            return null

        for (index in 0 until plays.lastIndex)
            if (plays[index] != plays[index + 1])
                return null

        return if (plays.first() == PlayerOne.symbol)
            PlayerOne.number
        else
            PlayerTwo.number
    }

    enum class Result {
        InProgress,
        PlayerOneWon,
        PlayerTwoWon,
        Tie,
    }
}
