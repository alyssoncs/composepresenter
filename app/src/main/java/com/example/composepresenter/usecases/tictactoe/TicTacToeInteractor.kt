package com.example.composepresenter.usecases.tictactoe

import com.example.composepresenter.entities.TicTacToe
import com.example.composepresenter.usecases.infra.TicTacToeRepository

class TicTacToeInteractor(
    private val ticTacToeRepository: TicTacToeRepository
) : TicTacToeUseCase {
    override fun newMatch(): TicTacToeResult {

        return TicTacToeResult(
            hasWinner = false,
            isTie = false,
            winner = 0,
            history = emptyList()
        )
    }

    override fun play(currentMatch: TicTacToeResult, x: Int, y: Int): TicTacToeResult {
        val newMatch = currentMatch.newPlay(x, y)

        val game = getGameFrom(newMatch)
        analyseGame(game)

        return newResult(game, newMatch)
    }
    override fun getScore(): TicTacToeScore {
        return ticTacToeRepository.getScore()
    }

    override fun clearScore() {
        ticTacToeRepository.clearScore()
    }

    private fun analyseGame(game: TicTacToe) {
        when (game.getResult()) {
            TicTacToe.Result.PlayerOneWon -> ticTacToeRepository.increasePlayerOneScore()
            TicTacToe.Result.PlayerTwoWon -> ticTacToeRepository.increasePlayerTwoScore()
            else -> {}
        }
    }

    private fun getGameFrom(newMatch: TicTacToeResult): TicTacToe {
        val game = TicTacToe.newGame()

        try {
            newMatch.history.forEach { play -> game.play(play.first, play.second) }
        } catch (e: TicTacToe.InvalidMoveException) {
            val lastPlay = newMatch.history.last()
            throw TicTacToeUseCase.InvalidMoveException(lastPlay.first, lastPlay.second)
        }

        return game
    }

    private fun newResult(
        game: TicTacToe,
        newMatch: TicTacToeResult
    ): TicTacToeResult {
        val gameResult = game.getResult()

        val hasWinner = gameResult == TicTacToe.Result.PlayerOneWon || gameResult == TicTacToe.Result.PlayerTwoWon
        val isTie = gameResult == TicTacToe.Result.Tie
        val winner = if (gameResult == TicTacToe.Result.PlayerOneWon) 1 else 2

        return newMatch.copy(
            hasWinner = hasWinner,
            isTie = isTie,
            winner = winner
        )
    }
}