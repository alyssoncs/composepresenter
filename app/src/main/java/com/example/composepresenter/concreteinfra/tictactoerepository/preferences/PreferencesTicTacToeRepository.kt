package com.example.composepresenter.concreteinfra.tictactoerepository.preferences

import android.content.Context
import com.example.composepresenter.usecases.infra.TicTacToeRepository
import com.example.composepresenter.usecases.tictactoe.TicTacToeScore

class PreferencesTicTacToeRepository(
    context: Context
) : TicTacToeRepository {
    companion object {
        private const val PLAYER_ONE_SCORE = "PLAYER_ONE_SCORE"
        private const val PLAYER_TWO_SCORE = "PLAYER_TWO_SCORE"
    }

    private val preferences = context.getSharedPreferences("tictactoe", Context.MODE_PRIVATE)

    override fun increasePlayerOneScore() {
        increaseScore(PLAYER_ONE_SCORE)
    }

    override fun increasePlayerTwoScore() {
        increaseScore(PLAYER_TWO_SCORE)
    }

    override fun getScore(): TicTacToeScore {
        return TicTacToeScore(
            playerOneScore = preferences.getInt(PLAYER_ONE_SCORE, 0),
            playerTwoScore = preferences.getInt(PLAYER_TWO_SCORE, 0),
        )
    }

    override fun clearScore() {
        with(preferences.edit()) {
            remove(PLAYER_ONE_SCORE)
            remove(PLAYER_TWO_SCORE)
            commit()
        }
    }

    private fun increaseScore(key: String) {
        val score = preferences.getInt(key, 0).inc()
        with(preferences.edit()) {
            putInt(key, score)
            commit()
        }
    }
}