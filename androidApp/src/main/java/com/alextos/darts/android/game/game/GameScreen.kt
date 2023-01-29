package com.alextos.darts.android.game.game

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.FAB
import com.alextos.darts.android.common.presentation.ScreenType
import com.alextos.darts.android.common.presentation.views.GameHistoryView
import com.alextos.darts.android.common.presentation.rememberScreenType
import com.alextos.darts.android.common.presentation.screens.TabletScreen
import com.alextos.darts.game.presentation.game.GameEvent
import com.alextos.darts.game.presentation.game.GameState
import com.alextos.darts.game.presentation.game.TurnState

@OptIn(ExperimentalAnimationApi::class)
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
        when (rememberScreenType()) {
            is ScreenType.Compact -> {
                AnimatedContent(targetState = state.isInputVisible) { isInputVisible ->
                    if (isInputVisible) {
                        GameInput(state = state, onEvent = onEvent)
                    } else {
                        GameHistory(paddingValues = it, state = state, onEvent = onEvent)
                    }
                }
            }
            is ScreenType.Large -> {
                TabletScreen(
                    content1 = {
                        GameHistory(paddingValues = it, state = state, onEvent = onEvent)
                    },
                    content2 = {
                        GameInput(state = state, onEvent = onEvent)
                    }
                )
            }
        }

        when (val turnState = state.turnState) {
            is TurnState.IsOver -> {
                TurnOverDialog(result = turnState.result, onEvent = onEvent)
            }
            else -> {}
        }

        if (state.isCloseGameDialogOpened) {
            CloseGameDialog(onEvent = onEvent)
        }

        if (state.isGameFinished) {
            GameFinishedDialog(winner = state.getWinnerName() ?: "", onEvent = onEvent)
        }
    }
}

@Composable
private fun GameHistory(
    paddingValues: PaddingValues,
    state: GameState,
    onEvent: (GameEvent) -> Unit
) {
    GameHistoryView(
        gameHistory = state.gameHistory,
        goal = state.gameGoal,
        currentPage = state.currentPage(),
        padding = paddingValues,
        onSelect = { turns, set ->
            onEvent(GameEvent.ShowDarts(turns, set))
        }
    )
}

@Composable
private fun GameInput(state: GameState, onEvent: (GameEvent) -> Unit) {
    GameInputView(
        currentSet = state.getCurrentSet(),
        playerName = state.currentPlayer?.name ?: "",
        leaderScore = state.leaderResult()
    ) { sector ->
        onEvent(GameEvent.MakeShot(sector))
    }
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
private fun GameFinishedDialog(winner: String, onEvent: (GameEvent) -> Unit) {
    AlertDialog(
        onDismissRequest = {  },
        title = {
            Text(text = stringResource(id = R.string.game_finished))
        },
        text = {
            Text(text = stringResource(id = R.string.winner, winner))
        },
        confirmButton = {
            Button(onClick = { onEvent(GameEvent.CloseGame) }) {
                Text(text = stringResource(id = R.string.finish_game))
            }
        }
    )
}