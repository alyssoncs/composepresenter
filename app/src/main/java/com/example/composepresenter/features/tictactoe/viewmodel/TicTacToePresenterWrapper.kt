package com.example.composepresenter.features.tictactoe.viewmodel

import androidx.lifecycle.ViewModel
import com.example.composepresenter.features.tictactoe.presentation.TicTacToePresenter

class TicTacToePresenterWrapper(
    private val presenter: TicTacToePresenter
) : ViewModel(), TicTacToePresenter by presenter
