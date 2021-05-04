package com.example.composepresenter.usecases.doubles

import com.example.composepresenter.usecases.infra.TicTacToeRepository
import com.example.composepresenter.usecases.tictactoe.TicTacToeScore

class TicTacToeRepositoryMock : TicTacToeRepository {
    private var playerOneScore = 0
    private var playerTwoScore = 0

    override fun increasePlayerOneScore() { playerOneScore++ }

    override fun increasePlayerTwoScore() { playerTwoScore++ }

    override fun getScore(): TicTacToeScore = TicTacToeScore(playerOneScore, playerTwoScore)

    override fun clearScore() { playerOneScore = 0; playerTwoScore = 0 }
}
