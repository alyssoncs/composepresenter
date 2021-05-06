package com.example.composepresenter

import android.app.Application
import com.example.composepresenter.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TicTacToeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TicTacToeApplication)

            modules(
                listOf(
                    ticTacToeRepositoryModule,
                    ticTacToeUseCaseModule,
                    ticTacToePresenterModule,
                    ticTacToeViewModelModule,
                )
            )
        }
    }

}