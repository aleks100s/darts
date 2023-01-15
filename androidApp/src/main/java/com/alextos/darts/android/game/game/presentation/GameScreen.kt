package com.alextos.darts.android.game.game.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.FAB
import com.alextos.darts.android.common.presentation.components.GameHistoryScreen
import com.alextos.darts.game.domain.models.Sector
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
            FAB(text = "Shot", icon = Icons.Filled.Dashboard) {
                onEvent(GameEvent.MakeShot(Sector.random()))
            }
        }
    ) {
        GameHistoryScreen(
            gameHistory = state.gameHistory,
            currentPage = state.currentPage(),
            padding = it
        )
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
    }
}