package com.example.composepresenter.ui.presentation

interface TicTacToePresenter {
    fun setView(view: TicTacToeView)
    fun onStart()
    fun onTileClick(x: Int, y: Int)
}