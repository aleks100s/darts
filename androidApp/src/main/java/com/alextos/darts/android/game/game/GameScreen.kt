package com.alextos.darts.android.game.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.ScreenType
import com.alextos.darts.android.common.presentation.views.GameHistoryView
import com.alextos.darts.android.common.presentation.rememberScreenType
import com.alextos.darts.android.common.presentation.screens.TabletScreen
import com.alextos.darts.android.common.presentation.views.GameInputView
import com.alextos.darts.game.presentation.game.GameEvent
import com.alextos.darts.game.presentation.game.GameState

@Composable
fun GameScreen(
    state: GameState,
    onEvent: (GameEvent) -> Unit
) {
    BackHandler {
        onEvent(GameEvent.BackButtonPressed)
    }

    when (rememberScreenType()) {
        is ScreenType.Compact -> {
            GameInput(state = state, onEvent = onEvent)
        }
        is ScreenType.Large -> {
            TabletScreen(
                content1 = {
                    GameHistory(state = state, onEvent = onEvent)
                },
                content2 = {
                    GameInput(state = state, onEvent = onEvent)
                }
            )
        }
    }

    if (state.isTurnOver()) {
        TurnOverDialog(result = state.turnResult(), onEvent = onEvent)
    }

    if (state.isCloseGameDialogOpened) {
        CloseGameDialog(onEvent = onEvent)
    }

    if (state.isGameFinished) {
        GameFinishedDialog(winner = state.getWinnerName(), onEvent = onEvent)
    }
}

@Composable
private fun GameHistory(
    state: GameState,
    onEvent: (GameEvent) -> Unit
) {
    GameHistoryView(
        gameHistory = state.gameHistory,
        goal = state.gameGoal,
        currentPage = state.currentPage(),
        padding = PaddingValues(),
        onSelect = { turns, currentPage ->
            onEvent(GameEvent.ShowDarts(turns, currentPage))
        }
    )
}

@Composable
private fun GameInput(
    state: GameState,
    onEvent: (GameEvent) -> Unit
) {
    GameInputView(
        currentTurn = state.getCurrentTurn(),
        results = state.currentResults(),
        eraseShot = { onEvent(GameEvent.EraseHit) },
        currentPlayerIndex = state.currentPage(),
        onInputClick = { sector ->
            onEvent(GameEvent.MakeShot(sector))
        },
        onPlayerClick = {
            onEvent(GameEvent.ShowHistory)
        }
    )
}

@Composable
private fun TurnOverDialog(result: Int, onEvent: (GameEvent) -> Unit) {
    AlertDialog(
        onDismissRequest = { onEvent(GameEvent.ChangeTurn) },
        title = {
            Text(text = stringResource(id = R.string.turn_is_over))
        },
        text = {
            Text(
                text = stringResource(
                    id = R.string.proceed_to_the_next_turn, result
                )
            )
        },
        confirmButton = {
            Button(onClick = { onEvent(GameEvent.ChangeTurn) }) {
                Text(text = stringResource(id = R.string.next_turn))
            }
        },
        dismissButton = {
            Button(
                onClick = { onEvent(GameEvent.ResetCurrentTurn) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Text(text = stringResource(id = R.string.reset_turn))
            }
        }
    )
}

@Composable
private fun CloseGameDialog(onEvent: (GameEvent) -> Unit) {
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

@Composable
private fun GameFinishedDialog(winner: String?, onEvent: (GameEvent) -> Unit) {
    AlertDialog(
        onDismissRequest = {  },
        title = {
            Text(text = stringResource(id = R.string.game_finished))
        },
        text = {
            winner?.let {
                Text(text = stringResource(id = R.string.winner, it))
            }
        },
        confirmButton = {
            Button(onClick = { onEvent(GameEvent.ReplayGame) }) {
                Text(text = stringResource(id = R.string.replay))
            }
        },
        dismissButton = {
            Button(onClick = { onEvent(GameEvent.CloseGame) }) {
                Text(text = stringResource(id = R.string.finish_game))
            }
        }
    )
}