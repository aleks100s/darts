package com.alextos.darts.android.game.game

import androidx.activity.compose.BackHandler
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.FAB
import com.alextos.darts.android.common.presentation.components.GameHistoryScreen
import com.alextos.darts.game.presentation.game.GameEvent
import com.alextos.darts.game.presentation.game.GameState

@Composable
fun GameScreen(
    state: GameState,
    onEvent: (GameEvent) -> Unit
) {
    BackHandler(true) {
        onEvent(GameEvent.BackButtonPressed)
    }
    Scaffold(
        floatingActionButton = {
            FAB(
                text = if (state.isInputVisible)
                    stringResource(id = R.string.history)
                else
                    stringResource(id = R.string.shoot),
                icon = if (state.isInputVisible)
                    Icons.Filled.List
                else
                    Icons.Filled.PlayArrow
            ) {
                onEvent(if (state.isInputVisible) GameEvent.HideGameInput else GameEvent.ShowGameInput)
            }
        }
    ) {
        if (state.isInputVisible) {
            GameInput(
                currentSet = state.getCurrentSet(),
                playerName = state.currentPlayer?.name ?: ""
            ) { sector ->
                onEvent(GameEvent.MakeShot(sector))
            }
        } else {
            GameHistoryScreen(
                gameHistory = state.gameHistory,
                currentPage = state.currentPage(),
                padding = it
            )
        }
        if (state.isTurnChangeDialogOpen) {
            AlertDialog(
                onDismissRequest = { onEvent(GameEvent.HideTurnChangeDialog) },
                title = {
                    Text(text = stringResource(id = R.string.turn_change_title))
                },
                text = {
                    Text(text = stringResource(
                        id = R.string.turn_change_text,
                        state.currentPlayer?.name ?: "")
                    )
                },
                confirmButton = {
                    Button(onClick = { onEvent(GameEvent.HideTurnChangeDialog) }) {
                        Text(text = stringResource(id = R.string.ok))
                    }
                }
            )
        }
        if (state.isCloseGameDialogOpened) {
            AlertDialog(
                onDismissRequest = { onEvent(GameEvent.ReturnToGame) },
                title = {
                    Text(text = stringResource(id = R.string.leave_game))
                },
                text = {
                    Text(text = stringResource(id = R.string.your_progress_will_be_lost))
                },
                confirmButton = {
                    Button(onClick = { onEvent(GameEvent.CloseGame) }) {
                        Text(text = stringResource(id = R.string.leave))
                    }
                },
                dismissButton = {
                    Button(onClick = { onEvent(GameEvent.ReturnToGame) }) {
                        Text(text = stringResource(id = R.string.return_to_game))
                    }
                }
            )
        }
        if (state.isGameFinished) {
            AlertDialog(
                onDismissRequest = {  },
                title = {
                    Text(text = stringResource(id = R.string.game_finished))
                },
                text = {
                    Text(text = stringResource(id = R.string.winner, state.getWinnerName() ?: ""))
                },
                confirmButton = {
                    Button(onClick = { onEvent(GameEvent.CloseGame) }) {
                        Text(text = stringResource(id = R.string.finish_game))
                    }
                }
            )
        }
    }
}