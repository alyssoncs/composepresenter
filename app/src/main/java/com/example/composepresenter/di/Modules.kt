package com.example.composepresenter.di

import com.example.composepresenter.concreteinfra.tictactoerepository.preferences.PreferencesTicTacToeRepository
import com.example.composepresenter.features.tictactoe.presentation.TicTacToePresenter
import com.example.composepresenter.features.tictactoe.presentation.TicTacToePresenterImpl
import com.example.composepresenter.features.tictactoe.viewmodel.TicTacToePresenterWrapper
import com.example.composepresenter.features.tictactoe.viewmodel.TicTacToeViewToViewModelAdapter
import com.example.composepresenter.usecases.infra.TicTacToeRepository
import com.example.composepresenter.usecases.tictactoe.TicTacToeInteractor
import com.example.composepresenter.usecases.tictactoe.TicTacToeUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ticTacToeRepositoryModule = module {
    single<TicTacToeRepository> {
        PreferencesTicTacToeRepository(
            context = get()
        )
    }
}

val ticTacToeUseCaseModule = module {
    factory<TicTacToeUseCase> {
        TicTacToeInteractor(
            ticTacToeRepository = get()
        )
    }
}

val ticTacToePresenterModule = module {
    factory<TicTacToePresenter> {
        TicTacToePresenterImpl(
            useCase = get()
        )
    }
}

val ticTacToeViewModelModule = module {
    viewModel {
        TicTacToePresenterWrapper(
            presenter = get()
        )
    }

    viewModel {
        TicTacToeViewToViewModelAdapter()
    }
}
