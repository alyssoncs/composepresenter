package com.example.composepresenter.features.tictactoe.presentation

interface TicTacToeView {
    fun clearBoard()
    fun setPlayerOneScore(score: Int)
    fun setPlayerTwoScore(score: Int)
    fun updatePlayerOneTile(x: Int, y: Int)
    fun updatePlayerTwoTile(x: Int, y: Int)
    fun notifyInvalidMove(x: Int, y: Int)
    fun notifyTie()
    fun notifyPlayerOneVictory()
    fun notifyPlayerTwoVictory()
}
