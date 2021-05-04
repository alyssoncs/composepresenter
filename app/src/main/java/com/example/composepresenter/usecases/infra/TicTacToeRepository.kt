package com.example.composepresenter.usecases.infra

import com.example.composepresenter.usecases.tictactoe.TicTacToeScore

interface TicTacToeRepository {
    fun increasePlayerOneScore()
    fun increasePlayerTwoScore()
    fun getScore(): TicTacToeScore
    fun clearScore()
}
