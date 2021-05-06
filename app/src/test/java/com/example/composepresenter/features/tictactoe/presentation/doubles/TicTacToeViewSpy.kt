package com.example.composepresenter.features.tictactoe.presentation.doubles

import com.example.composepresenter.features.tictactoe.presentation.TicTacToeView

class TicTacToeViewSpy : TicTacToeView {

    var clearBoardWasCalled = false
        private set

    var enableBoardWasCalled: Boolean = false
        private set

    var disableBoardWasCalled: Boolean = false
        private set

    var notifyTieWasCalled: Boolean = false
        private set

    var notifyPlayerOneVictoryWasCalled: Boolean = false
        private set

    var notifyPlayerTwoVictoryWasCalled: Boolean = false
        private set

    var playerOneScore = -1
        private set

    var playerTwoScore = -1
        private set

    var updatedPlayerOneTile: Pair<Int, Int>? = null
        private set

    var updatedPlayerTwoTile: Pair<Int, Int>? = null
        private set

    var invalidMove: Pair<Int, Int>? = null
        private set

    override fun clearBoard() { clearBoardWasCalled = true }

    override fun enableBoard() { enableBoardWasCalled = true }

    override fun disableBoard() { disableBoardWasCalled = true }

    override fun setPlayerOneScore(score: Int) { playerOneScore = score }

    override fun setPlayerTwoScore(score: Int) { playerTwoScore = score }

    override fun updatePlayerOneTile(x: Int, y: Int) { updatedPlayerOneTile = Pair(x, y) }

    override fun updatePlayerTwoTile(x: Int, y: Int) { updatedPlayerTwoTile = Pair(x, y) }

    override fun notifyInvalidMove(x: Int, y: Int) { invalidMove = Pair(x, y) }

    override fun notifyTie() { notifyTieWasCalled = true }

    override fun notifyPlayerOneVictory() { notifyPlayerOneVictoryWasCalled = true }

    override fun notifyPlayerTwoVictory() { notifyPlayerTwoVictoryWasCalled = true }
}
