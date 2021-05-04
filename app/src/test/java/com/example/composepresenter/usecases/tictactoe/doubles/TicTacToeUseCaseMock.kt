package com.example.composepresenter.usecases.tictactoe.doubles

import com.example.composepresenter.usecases.tictactoe.TicTacToeResult
import com.example.composepresenter.usecases.tictactoe.TicTacToeScore
import com.example.composepresenter.usecases.tictactoe.TicTacToeUseCase

class TicTacToeUseCaseMock : TicTacToeUseCase {
    private val emptyResult = TicTacToeResult(
        hasWinner = false,
        isTie = false,
        winner = 0,
        history = emptyList(),
    )

    private val emptyScore = TicTacToeScore(0, 0)

    private var result: TicTacToeResult = emptyResult
    private var score: TicTacToeScore = emptyScore
    private var shouldFail = false

    fun setResult(result: TicTacToeResult) {
        this.result = result
    }

    fun setScore(score: TicTacToeScore) {
        this.score = score
    }

    override fun newMatch(): TicTacToeResult {
        return emptyResult
    }

    override fun play(currentMatch: TicTacToeResult, x: Int, y: Int): TicTacToeResult {
        if (shouldFail)
            throw TicTacToeUseCase.InvalidMoveException(x, y)
        else
            return result
    }

    override fun getScore(): TicTacToeScore {
        return score
    }

    override fun clearScore() {
        score = emptyScore
    }

    fun setFailingMode() {
        shouldFail = true
    }
}
