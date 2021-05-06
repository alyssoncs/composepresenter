package com.example.composepresenter.usecases.tictactoe

import com.example.composepresenter.concreteinfra.tictactoerepository.inmemory.InMemoryTicTacToeRepository
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TicTacToeInteractorTest {
    private lateinit var useCase: TicTacToeUseCase

    @BeforeEach
    fun setUp() {
        useCase = TicTacToeInteractor(InMemoryTicTacToeRepository())
    }

    @Test
    fun `new match`() {
        val match = useCase.newMatch()

        assertThat(match.hasWinner).isFalse()
    }

    @Test
    fun play() {
        var currentMatch = useCase.newMatch()

        currentMatch = useCase.play(currentMatch, 0, 0)
        currentMatch = useCase.play(currentMatch, 0, 1)
        currentMatch = useCase.play(currentMatch, 0, 2)

        val board = currentMatch.getBoard()
        assertThat(currentMatch.hasWinner).isFalse()
        assertThat(currentMatch.isTie).isFalse()
        assertThat(board[0][0]).isEqualTo(TicTacToePlay.PlayerOne)
        assertThat(board[0][1]).isEqualTo(TicTacToePlay.PlayerTwo)
        assertThat(board[0][2]).isEqualTo(TicTacToePlay.PlayerOne)
        assertThat(board[1][0]).isEqualTo(TicTacToePlay.Empty)
        assertThat(board[1][1]).isEqualTo(TicTacToePlay.Empty)
        assertThat(board[1][2]).isEqualTo(TicTacToePlay.Empty)
        assertThat(board[2][0]).isEqualTo(TicTacToePlay.Empty)
        assertThat(board[2][1]).isEqualTo(TicTacToePlay.Empty)
        assertThat(board[2][2]).isEqualTo(TicTacToePlay.Empty)
    }

    @Test
    fun `player one wins`() {
        val currentMatch = playerOneWins()

        assertThat(currentMatch.hasWinner).isTrue()
        assertThat(currentMatch.isTie).isFalse()
        assertThat(currentMatch.winner).isEqualTo(1)
    }

    @Test
    fun `player two wins`() {
        val currentMatch = playerTwoWins()

        assertThat(currentMatch.hasWinner).isTrue()
        assertThat(currentMatch.isTie).isFalse()
        assertThat(currentMatch.winner).isEqualTo(2)
    }

    @Test
    fun tie() {
        val currentMatch = tieGame()

        assertThat(currentMatch.hasWinner).isFalse()
        assertThat(currentMatch.isTie).isTrue()
    }

    @Test
    fun `invalid move`() {
        var currentMatch = useCase.newMatch()

        currentMatch = useCase.play(currentMatch, 0, 0)

        try {
            useCase.play(currentMatch, 0, 0)
            fail()
        } catch (e: TicTacToeUseCase.InvalidMoveException) {
            assertThat(e).isEqualTo(TicTacToeUseCase.InvalidMoveException(0, 0))
        }
    }

    @Test
    fun `empty score`() {
        val expectedScore = TicTacToeScore(0, 0)
        val actualScore = useCase.getScore()

        assertThat(actualScore).isEqualTo(expectedScore)
    }

    @Test
    fun `game in progress leads to empty score`() {
        inProgressGame()
        val expectedScore = TicTacToeScore(0, 0)

        val actualScore = useCase.getScore()

        assertThat(actualScore).isEqualTo(expectedScore)
    }

    @Test
    fun `tie game leads to empty score`() {
        tieGame()
        val expectedScore = TicTacToeScore(0, 0)

        val actualScore = useCase.getScore()

        assertThat(actualScore).isEqualTo(expectedScore)
    }

    @Test
    fun `player one wins leads to increase in player one score`() {
        playerOneWins()
        val expectedScore = TicTacToeScore(1, 0)

        val actualScore = useCase.getScore()

        assertThat(actualScore).isEqualTo(expectedScore)
    }

    @Test
    fun `player two wins leads to increase in player two score`() {
        playerTwoWins()
        val expectedScore = TicTacToeScore(0, 1)

        val actualScore = useCase.getScore()

        assertThat(actualScore).isEqualTo(expectedScore)
    }

    @Test
    fun `tie and in progress game doesn't affect current score`() {
        playerOneWins()
        inProgressGame()
        playerTwoWins()
        tieGame()
        playerTwoWins()
        val expectedScore = TicTacToeScore(1, 2)

        val actualScore = useCase.getScore()

        assertThat(actualScore).isEqualTo(expectedScore)
    }

    @Test
    fun `score is cleared`() {
        playerOneWins()
        playerTwoWins()
        playerTwoWins()

        val previousScore = useCase.getScore()
        useCase.clearScore()
        val clearedScore = useCase.getScore()

        assertThat(previousScore).isEqualTo(TicTacToeScore(1, 2))
        assertThat(clearedScore).isEqualTo(TicTacToeScore(0, 0))
    }

    private fun playerOneWins(): TicTacToeResult {
        var currentMatch = useCase.newMatch()

        currentMatch = useCase.play(currentMatch, 0, 0)
        currentMatch = useCase.play(currentMatch, 0, 1)
        currentMatch = useCase.play(currentMatch, 1, 0)
        currentMatch = useCase.play(currentMatch, 0, 2)
        currentMatch = useCase.play(currentMatch, 2, 0)
        return currentMatch
    }

    private fun playerTwoWins(): TicTacToeResult {
        var currentMatch = useCase.newMatch()

        currentMatch = useCase.play(currentMatch, 0, 0)
        currentMatch = useCase.play(currentMatch, 0, 1)
        currentMatch = useCase.play(currentMatch, 1, 0)
        currentMatch = useCase.play(currentMatch, 1, 1)
        currentMatch = useCase.play(currentMatch, 2, 2)
        currentMatch = useCase.play(currentMatch, 2, 1)
        return currentMatch
    }

    private fun tieGame(): TicTacToeResult {
        var currentMatch = useCase.newMatch()

        currentMatch = useCase.play(currentMatch, 0, 0)
        currentMatch = useCase.play(currentMatch, 1, 0)
        currentMatch = useCase.play(currentMatch, 2, 2)
        currentMatch = useCase.play(currentMatch, 1, 1)
        currentMatch = useCase.play(currentMatch, 1, 2)
        currentMatch = useCase.play(currentMatch, 0, 2)
        currentMatch = useCase.play(currentMatch, 2, 0)
        currentMatch = useCase.play(currentMatch, 2, 1)
        currentMatch = useCase.play(currentMatch, 0, 1)
        return currentMatch
    }

    private fun inProgressGame(): TicTacToeResult {
        var currentMatch = useCase.newMatch()

        currentMatch = useCase.play(currentMatch, 0, 0)
        currentMatch = useCase.play(currentMatch, 1, 0)
        currentMatch = useCase.play(currentMatch, 2, 2)

        return currentMatch
    }
}