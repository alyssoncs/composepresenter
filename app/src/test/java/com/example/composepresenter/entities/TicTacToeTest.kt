package com.example.composepresenter.entities

import com.example.composepresenter.entities.TicTacToe.*
import com.example.composepresenter.entities.TicTacToe.Result.*
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.reflect.Executable

class TicTacToeTest {
    private lateinit var game: TicTacToe

    @BeforeEach
    fun setUp() {
        game = TicTacToe.newGame()
    }

    @Test
    fun `new game`() {
        val winner = game.getResult()

        assertThat(winner).isEqualTo(InProgress)
    }

    @Test
    fun `one play`() {
        game.play(0, 0)

        val winner = game.getResult()

        assertThat(winner).isEqualTo(InProgress)
    }

    @Test
    fun `player one wins horizontally`() {
        game.apply {
            play(0, 0)
            play(1, 0)
            play(0, 1)
            play(1, 1)
            play(0, 2)
        }

        val winner = game.getResult()

        assertThat(winner).isEqualTo(PlayerOneWon)
    }

    @Test
    fun `player one wins vertically`() {
        game.apply {
            play(0, 0)
            play(0, 1)
            play(1, 0)
            play(1, 1)
            play(2, 0)
        }

        val winner = game.getResult()

        assertThat(winner).isEqualTo(PlayerOneWon)
    }


    @Test
    fun `player one wins on main diagonal`() {
        game.apply {
            play(0, 0)
            play(1, 0)
            play(1, 1)
            play(1, 2)
            play(2, 2)
        }

        val winner = game.getResult()

        assertThat(winner).isEqualTo(PlayerOneWon)
    }

    @Test
    fun `player one wins on anti diagonal`() {
        game.apply {
            play(0, 2)
            play(1, 0)
            play(1, 1)
            play(1, 2)
            play(2, 0)
        }

        val winner = game.getResult()

        assertThat(winner).isEqualTo(PlayerOneWon)
    }

    @Test
    fun `player two wins horizontally`() {
        game.apply {
            play(0, 0)
            play(1, 0)
            play(0, 1)
            play(1, 1)
            play(2, 0)
            play(1, 2)
        }

        val winner = game.getResult()

        assertThat(winner).isEqualTo(PlayerTwoWon)
    }

    @Test
    fun `player two wins vertically`() {
        game.apply {
            play(0, 0)
            play(0, 1)
            play(1, 0)
            play(1, 1)
            play(0, 2)
            play(2, 1)
        }

        val winner = game.getResult()

        assertThat(winner).isEqualTo(PlayerTwoWon)
    }

    @Test
    fun `player two wins on main diagonal`() {
        game.apply {
            play(0, 1)
            play(0, 0)
            play(0, 2)
            play(1, 1)
            play(1, 2)
            play(2, 2)
        }

        val winner = game.getResult()

        assertThat(winner).isEqualTo(PlayerTwoWon)
    }

    @Test
    fun `player two wins on anti diagonal`() {
        game.apply {
            play(0, 0)
            play(0, 2)
            play(0, 1)
            play(1, 1)
            play(1, 0)
            play(2, 0)
        }

        val winner = game.getResult()

        assertThat(winner).isEqualTo(PlayerTwoWon)
    }

    @Test
    fun tie() {
        game.apply {
            play(0, 0)
            play(1, 0)
            play(2, 2)
            play(1, 1)
            play(1, 2)
            play(0, 2)
            play(2, 0)
            play(2, 1)
            play(0, 1)
        }

        val winner = game.getResult()

        assertThat(winner).isEqualTo(Tie)
    }

    @Test
    fun `play on filled tile`() {
        game.apply {
            play(0, 0)
            play(1, 0)
        }

        assertThrows<InvalidMoveException> { game.play(0, 0) }
    }

    @Test
    fun `out of bounds`() {
        assertThrows<InvalidMoveException> { game.play(3, 0) }
        assertThrows<InvalidMoveException> { game.play(0, 3) }
        assertThrows<InvalidMoveException> { game.play(-1, 0) }
        assertThrows<InvalidMoveException> { game.play(0, 4) }
    }

    @Test
    fun `get board`() {
        game.apply {
            play(0, 0)
            play(1, 0)
            play(2, 2)
            play(1, 1)
            play(1, 2)
            play(0, 2)
            play(2, 0)
            play(2, 1)
        }

        val board = game.getBoard()

        assertThat(board[0][0]).isEqualTo(Play.PlayerOne)
        assertThat(board[1][0]).isEqualTo(Play.PlayerTwo)
        assertThat(board[2][2]).isEqualTo(Play.PlayerOne)
        assertThat(board[1][1]).isEqualTo(Play.PlayerTwo)
        assertThat(board[1][2]).isEqualTo(Play.PlayerOne)
        assertThat(board[0][2]).isEqualTo(Play.PlayerTwo)
        assertThat(board[2][0]).isEqualTo(Play.PlayerOne)
        assertThat(board[2][1]).isEqualTo(Play.PlayerTwo)
        assertThat(board[0][1]).isEqualTo(Play.Empty)
    }
}