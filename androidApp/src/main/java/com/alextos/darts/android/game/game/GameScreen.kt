package com.alextos.darts.android.game.game

import androidx.activity.compose.BackHandler
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.ScreenType
import com.alextos.darts.android.common.presentation.views.GameHistoryView
import com.alextos.darts.android.common.presentation.rememberScreenType
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.screens.SplitScreen
import com.alextos.darts.android.common.presentation.views.GameInputView
import com.alextos.darts.game.presentation.game.GameEvent
import com.alextos.darts.game.presentation.game.GameResult
import com.alextos.darts.game.presentation.game.GameState

@Composable
fun GameScreen(
    state: GameState,
    onEvent: (GameEvent) -> Unit
) {
    BackHandler {
        onEvent(GameEvent.BackButtonPressed)
    }

    val title = stringResource(id = R.string.game_title_with_turn_counter, state.turnNumber)

    when (val screenType = rememberScreenType()) {
        is ScreenType.Compact -> {
            Screen(
                title = title,
                backIcon = Icons.Filled.Close,
                onBackPressed = { onEvent(GameEvent.BackButtonPressed) },
                additionalNavBarContent = {
                    InfoButton {
                        onEvent(GameEvent.ShowGameSettings)
                    }
                }
            ) { modifier ->
                GameInput(
                    modifier = modifier,
                    screenType = screenType,
                    state = state,
                    onEvent = onEvent
                )
            }
        }
        is ScreenType.Large -> {
            SplitScreen(
                title = title,
                isBackButtonVisible = false,
                content1 = {
                    GameInput(
                        modifier = Modifier,
                        screenType = screenType,
                        state = state,
                        onEvent = onEvent
                    )
                },
                content2 = {
                    GameHistory(state = state, onEvent = onEvent)
                },
                additionalNavBarContent = {
                    InfoButton {
                        onEvent(GameEvent.ShowGameSettings)
                    }
                },
                onBackPressed = {
                    onEvent(GameEvent.BackButtonPressed)
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

    state.gameResult?.let {
        GameFinishedDialog(gameResult = it, onEvent = onEvent)
    }
}

@Composable
private fun GameHistory(
    state: GameState,
    onEvent: (GameEvent) -> Unit
) {
    GameHistoryView(
        modifier = Modifier,
        gameHistory = state.gameHistory,
        goal = state.gameGoal,
        currentPage = state.currentPage(),
        onSelect = { turns, currentPage ->
            onEvent(GameEvent.ShowDarts(turns, currentPage))
        }
    )
}

@Composable
private fun GameInput(
    modifier: Modifier,
    screenType: ScreenType,
    state: GameState,
    onEvent: (GameEvent) -> Unit
) {
    GameInputView(
        modifier = modifier,
        currentTurn = state.getCurrentTurn(),
        results = state.currentResults(),
        eraseShot = { onEvent(GameEvent.EraseHit) },
        isStatisticsEnabled = state.isStatisticsEnabled,
        currentPlayerIndex = state.currentPage(),
        onInputClick = { sector ->
            onEvent(GameEvent.MakeShot(sector))
        },
        onPlayerClick = {
            when (screenType) {
                is ScreenType.Compact -> onEvent(GameEvent.ShowHistory(it))
                else -> {}
            }
        }
    )
}

@Composable
private fun TurnOverDialog(result: Int, onEvent: (GameEvent) -> Unit) {
    AlertDialog(
        onDismissRequest = { },
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
        },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
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
private fun GameFinishedDialog(gameResult: GameResult, onEvent: (GameEvent) -> Unit) {
    val title = when (gameResult) {
        is GameResult.TrainingFinished -> {
            stringResource(id = R.string.training_finished)
        }
        is GameResult.Draw -> {
            stringResource(id = R.string.game_finished)
        }
        is GameResult.Winner -> {
            stringResource(id = R.string.game_finished)
        }
    }
    val text = when (gameResult) {
        is GameResult.TrainingFinished -> {
            ""
        }
        is GameResult.Draw -> {
            stringResource(id = R.string.game_result_is_draw)
        }
        is GameResult.Winner -> {
            stringResource(id = R.string.winner, gameResult.name)
        }
    }
    AlertDialog(
        onDismissRequest = {  },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
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
        },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    )
}

@Composable
private fun InfoButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = stringResource(id = R.string.game_settings)
        )
    }
}