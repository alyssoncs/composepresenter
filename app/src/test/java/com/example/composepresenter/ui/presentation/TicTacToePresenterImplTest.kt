package com.example.composepresenter.ui.presentation

import com.example.composepresenter.ui.presentation.doubles.TicTacToeViewSpy
import com.example.composepresenter.usecases.tictactoe.TicTacToeResult
import com.example.composepresenter.usecases.tictactoe.TicTacToeScore
import com.example.composepresenter.usecases.tictactoe.doubles.TicTacToeUseCaseMock
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.math.exp

class TicTacToePresenterImplTest {
    private lateinit var useCaseMock: TicTacToeUseCaseMock
    private lateinit var viewSpy: TicTacToeViewSpy
    private lateinit var presenter: TicTacToePresenter

    @BeforeEach
    fun setUp() {
        useCaseMock = TicTacToeUseCaseMock()
        viewSpy = TicTacToeViewSpy()
        presenter = TicTacToePresenterImpl(useCaseMock)
        presenter.setView(viewSpy)
    }

    @Test
    fun `on start`() {
        val expectedScore = TicTacToeScore(0, 3)
        useCaseMock.setScore(expectedScore)

        presenter.onStart()

        assertThat(viewSpy.clearBoardWasCalled).isTrue()
        assertThat(viewSpy.playerOneScore).isEqualTo(expectedScore.playerOneScore)
        assertThat(viewSpy.playerTwoScore).isEqualTo(expectedScore.playerTwoScore)
    }

    @Test
    fun `player one click tile`() {
        val expectedResult = TicTacToeResult(
            hasWinner = false,
            isTie = false,
            winner = 0,
            history = listOf(Pair(0, 0), Pair(1, 1), Pair(2, 2))
        )
        useCaseMock.setResult(expectedResult)

        presenter.onTileClick(0, 0)

        assertThat(viewSpy.updatedPlayerOneTile).isEqualTo(expectedResult.history.last())
        assertThat(viewSpy.notifyTieWasCalled).isFalse()
    }

    @Test
    fun `player two click tile`() {
        val expectedResult = TicTacToeResult(
            hasWinner = false,
            isTie = false,
            winner = 0,
            history = listOf(Pair(0, 0), Pair(1, 1))
        )
        useCaseMock.setResult(expectedResult)

        presenter.onTileClick(0, 0)

        assertThat(viewSpy.updatedPlayerTwoTile).isEqualTo(expectedResult.history.last())
        assertThat(viewSpy.notifyTieWasCalled).isFalse()
    }

    @Test
    fun `on invalid move`() {
        val expectedResult = TicTacToeResult(
            hasWinner = false,
            isTie = false,
            winner = 0,
            history = listOf(Pair(1, 1))
        )
        useCaseMock.setResult(expectedResult)
        presenter.onTileClick(1, 1)
        useCaseMock.setFailingMode()

        presenter.onTileClick(1, 1)

        assertThat(viewSpy.invalidMove).isEqualTo(Pair(1, 1))
        assertThat(viewSpy.notifyTieWasCalled).isFalse()
    }

    @Test
    fun `on tie`() {
        val expectedResult = TicTacToeResult(
            hasWinner = false,
            isTie = true,
            winner = 0,
            history = listOf(Pair(1, 1))
        )
        useCaseMock.setResult(expectedResult)

        presenter.onTileClick(1, 1)

        assertThat(viewSpy.updatedPlayerOneTile).isEqualTo(Pair(1, 1))
        assertThat(viewSpy.notifyTieWasCalled).isTrue()
    }

    @Test
    fun `on player one victory`() {
        val expectedResult = TicTacToeResult(
            hasWinner = true,
            isTie = false,
            winner = 1,
            history = listOf(Pair(1, 1))
        )
        useCaseMock.setResult(expectedResult)

        presenter.onTileClick(1, 1)

        assertThat(viewSpy.updatedPlayerOneTile).isEqualTo(Pair(1, 1))
        assertThat(viewSpy.notifyPlayerOneVictoryWasCalled).isTrue()
        assertThat(viewSpy.notifyTieWasCalled).isFalse()
    }

    @Test
    fun `on player two victory`() {
        val expectedResult = TicTacToeResult(
            hasWinner = true,
            isTie = false,
            winner = 2,
            history = listOf(Pair(1, 1), Pair(2, 2))
        )
        useCaseMock.setResult(expectedResult)

        presenter.onTileClick(1, 1)

        assertThat(viewSpy.updatedPlayerTwoTile).isEqualTo(Pair(2, 2))
        assertThat(viewSpy.notifyPlayerTwoVictoryWasCalled).isTrue()
        assertThat(viewSpy.notifyPlayerOneVictoryWasCalled).isFalse()
        assertThat(viewSpy.notifyTieWasCalled).isFalse()
    }
}