package com.example.composepresenter.features.tictactoe.presentation

import com.example.composepresenter.usecases.tictactoe.TicTacToeResult
import com.example.composepresenter.usecases.tictactoe.TicTacToeUseCase

class TicTacToePresenterImpl(private val useCase: TicTacToeUseCase) : TicTacToePresenter {
    private lateinit var view: TicTacToeView

    private var currentMatch: TicTacToeResult = useCase.newMatch()

    override fun setView(view: TicTacToeView) {
        this.view = view
    }

    override fun onStart() {
        currentMatch = useCase.newMatch()
        val score = useCase.getScore()

        view.apply {
            clearBoard()
            setPlayerOneScore(score.playerOneScore)
            setPlayerTwoScore(score.playerTwoScore)
        }
    }

    override fun onTileClick(x: Int, y: Int) {
        try {
            play(x, y)
            updateViewAfterPlay()
        } catch (e: TicTacToeUseCase.InvalidMoveException) {
            view.notifyInvalidMove(e.x, e.y)
        }
    }

    private fun updateViewAfterPlay() {
        updateViewTile()
        if (currentMatch.hasWinner)
            updateViewOnWinner()
        else if (currentMatch.isTie)
            updateViewOnTie()
    }

    private fun updateViewTile() {
        val lastPlay = currentMatch.history.last()
        if (isPlayerOneTurn())
            view.updatePlayerOneTile(lastPlay.first, lastPlay.second)
        else
            view.updatePlayerTwoTile(lastPlay.first, lastPlay.second)
    }

    private fun updateViewOnWinner() {
        if (currentMatch.winner == 1)
            view.notifyPlayerOneVictory()
        else
            view.notifyPlayerTwoVictory()
    }

    private fun updateViewOnTie() {
        view.notifyTie()
    }

    private fun play(x: Int, y: Int) {
        currentMatch = useCase.play(currentMatch, x, y)
    }

    private fun isPlayerOneTurn() = currentMatch.history.lastIndex % 2 == 0
}