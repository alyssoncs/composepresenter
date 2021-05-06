package com.example.composepresenter.features.tictactoe.presentation

interface TicTacToePresenter {
    fun setView(view: TicTacToeView)
    fun onStart()
    fun onTileClick(x: Int, y: Int)
    fun onClearScore()
}