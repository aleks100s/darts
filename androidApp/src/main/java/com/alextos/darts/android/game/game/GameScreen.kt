package com.alextos.darts.android.game.game

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.FAB
import com.alextos.darts.android.common.presentation.components.GameHistoryView
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
        AnimatedContent(targetState = state.isInputVisible) { isInputVisible ->
            if (isInputVisible) {
                GameInputView(
                    currentSet = state.getCurrentSet(),
                    playerName = state.currentPlayer?.name ?: "",
                    leaderScore = state.leaderResult()
                ) { sector ->
                    onEvent(GameEvent.MakeShot(sector))
                }
            } else {
                GameHistoryView(
                    gameHistory = state.gameHistory,
                    currentPage = state.currentPage(),
                    padding = it,
                    onSelect = { turns, set ->
                        onEvent(GameEvent.ShowDarts(turns, set))
                    }
                )
            }
        }

        when (val turnState = state.turnState) {
            is TurnState.IsOver -> {
                AlertDialog(
                    onDismissRequest = { onEvent(GameEvent.ChangeTurn) },
                    title = {
                        Text(text = stringResource(id = R.string.turn_is_over))
                    },
                    text = {
                        Text(
                            text = stringResource(
                                id = R.string.proceed_to_the_next_turn, turnState.result
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
            else -> {}
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