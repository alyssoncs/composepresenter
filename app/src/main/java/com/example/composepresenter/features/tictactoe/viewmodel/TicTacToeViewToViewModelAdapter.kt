package com.example.composepresenter.features.tictactoe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composepresenter.features.tictactoe.presentation.TicTacToeView
import com.example.composepresenter.libraries.mvvmevent.MvvmEvent

class TicTacToeViewToViewModelAdapter : ViewModel(), TicTacToeView {
    companion object {
        private const val BOARD_SIZE = 3
    }

    sealed class Snackbar {
        data class InvalidMove(val x: Int, val y: Int): Snackbar()
        object Tie: Snackbar()
        object PlayerOneVictory : Snackbar()
        object PlayerTwoVictory : Snackbar()
    }

    enum class Tile {
        Empty,
        PlayerOne,
        PlayerTwo,
    }

    private val _board = List(BOARD_SIZE) { List(BOARD_SIZE) { MutableLiveData(Tile.Empty) } }
    val board: List<List<LiveData<Tile>>> = _board

    private val _playerOneScore = MutableLiveData<Int>()
    val playerOneScore: LiveData<Int> = _playerOneScore

    private val _playerTwoScore = MutableLiveData<Int>()
    val playerTwoScore: LiveData<Int> = _playerTwoScore

    private val _snackbar = MutableLiveData<MvvmEvent<Snackbar>>()
    val snackbar: LiveData<MvvmEvent<Snackbar>> = _snackbar

    private val _isBoardEnabled = MutableLiveData<Boolean>(true)
    val isBoardEnabled: LiveData<Boolean> = _isBoardEnabled

    override fun clearBoard() {
        _board.forEach { row ->
            row.forEach { tile ->
                tile.value = Tile.Empty
            }
        }
    }

    override fun enableBoard() {
        _isBoardEnabled.value = true
    }

    override fun disableBoard() {
        _isBoardEnabled.value = false
    }

    override fun setPlayerOneScore(score: Int) {
        _playerOneScore.value = score
    }

    override fun setPlayerTwoScore(score: Int) {
        _playerTwoScore.value = score
    }

    override fun updatePlayerOneTile(x: Int, y: Int) {
        _board[x][y].value = Tile.PlayerOne
    }

    override fun updatePlayerTwoTile(x: Int, y: Int) {
        _board[x][y].value = Tile.PlayerTwo
    }

    override fun notifyInvalidMove(x: Int, y: Int) {
        _snackbar.value = MvvmEvent(Snackbar.InvalidMove(x, y))
    }

    override fun notifyTie() {
        _snackbar.value = MvvmEvent(Snackbar.Tie)
    }

    override fun notifyPlayerOneVictory() {
        _snackbar.value = MvvmEvent(Snackbar.PlayerOneVictory)
    }

    override fun notifyPlayerTwoVictory() {
        _snackbar.value = MvvmEvent(Snackbar.PlayerTwoVictory)
    }
}