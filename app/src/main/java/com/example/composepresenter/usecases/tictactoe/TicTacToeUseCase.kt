package com.example.composepresenter.usecases.tictactoe

interface TicTacToeUseCase {
    data class InvalidMoveException(val x: Int, val y: Int) : Throwable()

    fun newMatch(): TicTacToeResult
    fun play(currentMatch: TicTacToeResult, x: Int, y: Int): TicTacToeResult
    fun getScore(): TicTacToeScore
    fun clearScore()
}
