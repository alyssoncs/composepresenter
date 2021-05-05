package com.example.composepresenter.features.tictactoe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composepresenter.features.tictactoe.presentation.TicTacToeView
import com.example.composepresenter.libraries.mvvmevent.MvvmEvent

class TicTacToeViewState : ViewModel(), TicTacToeView {
    companion object {
        private const val BOARD_SIZE = 3
    }

    sealed class Toast {
        data class InvalidMove(val x: Int, val y: Int): Toast()
        object Tie: Toast()
        object PlayerOneVictory : Toast()
        object PlayerTwoVictory : Toast()
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

    private val _toast = MutableLiveData<MvvmEvent<Toast>>()
    val toast: LiveData<MvvmEvent<Toast>> = _toast

    override fun clearBoard() {
        _board.forEach { row ->
            row.forEach { tile ->
                tile.value = Tile.Empty
            }
        }
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
        _toast.value = MvvmEvent(Toast.InvalidMove(x, y))
    }

    override fun notifyTie() {
        _toast.value = MvvmEvent(Toast.Tie)
    }

    override fun notifyPlayerOneVictory() {
        _toast.value = MvvmEvent(Toast.PlayerOneVictory)
    }

    override fun notifyPlayerTwoVictory() {
        _toast.value = MvvmEvent(Toast.PlayerTwoVictory)
    }
}