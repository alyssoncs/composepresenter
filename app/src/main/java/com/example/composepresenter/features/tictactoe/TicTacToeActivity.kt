package com.example.composepresenter.features.tictactoe

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composepresenter.features.tictactoe.viewmodel.TicTacToePresenterWrapper
import com.example.composepresenter.features.tictactoe.viewmodel.TicTacToeViewToViewModelAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicTacToeActivity : ComponentActivity() {

    private val presenter by viewModel<TicTacToePresenterWrapper>()
    private val viewState by viewModel<TicTacToeViewToViewModelAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToe()
        }

        if (savedInstanceState == null) {
            presenter.setView(viewState)
            presenter.onStart()
        }
    }

    @Composable
    fun TicTacToe() {
        val snackbarState by viewState.snackbar.observeAsState()
        val scaffoldState = rememberScaffoldState()
        val snackbarCoroutineScope = rememberCoroutineScope()

        MaterialTheme {
            Scaffold(scaffoldState = scaffoldState) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    GameScore()
                    Spacer(modifier = Modifier.size(16.dp))
                    TicTacToeBoard()
                }
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                ) {
                    Button(
                        onClick = { presenter.onStart() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(text = "RESTART", fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Button(
                        onClick = { presenter.onClearScore() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(text = "CLEAR SCORE", fontSize = 16.sp)
                    }
                }

                snackbarState?.getContentIfNotHandled()?.let {
                    val text = when (it) {
                        is TicTacToeViewToViewModelAdapter.Snackbar.InvalidMove -> "Invalid move at (${it.x + 1}, ${it.x + 1})"
                        is TicTacToeViewToViewModelAdapter.Snackbar.PlayerOneVictory -> "Player one wins"
                        is TicTacToeViewToViewModelAdapter.Snackbar.PlayerTwoVictory -> "Player two wins"
                        is TicTacToeViewToViewModelAdapter.Snackbar.Tie -> "Good game, it was a tie"
                    }
                    snackbarCoroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(text)
                    }
                }
            }
        }
    }

    @Composable
    private fun GameScore() {
        val playerOneScore by viewState.playerOneScore.observeAsState(0)
        val playerTwoScore by viewState.playerTwoScore.observeAsState(2)
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            PlayerScore("Player one", playerOneScore)
            PlayerScore("Player two", playerTwoScore)
        }
    }

    @Composable
    private fun PlayerScore(player: String, score: Int) {
        val typography = MaterialTheme.typography
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = player, style = typography.body1)
            Text(text = "$score", style = typography.h4)
        }
    }

    @Composable
    private fun TicTacToeBoard() {
        val board: List<List<State<TicTacToeViewToViewModelAdapter.Tile>>> =
            viewState.board.map { row ->
                row.map {
                    it.observeAsState(TicTacToeViewToViewModelAdapter.Tile.Empty)
                }
            }
        val isBoardEnabled by viewState.isBoardEnabled.observeAsState(true)
        Column {
            board.forEachIndexed() { x, row ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    row.forEachIndexed { y, tileState ->
                        Tile(x, y, isBoardEnabled, tileState.value)
                    }
                }
            }
        }
    }

    @Composable
    private fun Tile(
        i: Int,
        j: Int,
        isBoardEnabled: Boolean,
        tile: TicTacToeViewToViewModelAdapter.Tile
    ) {
        val buttonSize = 96.dp
        OutlinedButton(
            onClick = { presenter.onTileClick(i, j) },
            enabled = isBoardEnabled,
            modifier = Modifier
                .size(buttonSize)
                .padding(1.dp)
        ) {
            Text(
                text = when (tile) {
                    TicTacToeViewToViewModelAdapter.Tile.Empty -> " "
                    TicTacToeViewToViewModelAdapter.Tile.PlayerOne -> "X"
                    TicTacToeViewToViewModelAdapter.Tile.PlayerTwo -> "O"
                },
                color = if (isBoardEnabled) {
                    when (tile) {
                        TicTacToeViewToViewModelAdapter.Tile.Empty -> Color.Transparent
                        TicTacToeViewToViewModelAdapter.Tile.PlayerOne -> Color.Red
                        TicTacToeViewToViewModelAdapter.Tile.PlayerTwo -> Color.Blue
                    }
                } else {
                    Color.Gray
                }
            )
        }
    }

    @Preview
    @Composable
    fun DefaultPreview() {
        TicTacToe()
    }
}
