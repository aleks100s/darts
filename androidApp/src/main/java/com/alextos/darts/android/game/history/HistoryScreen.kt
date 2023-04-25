package com.alextos.darts.android.game.history

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.FAB
import com.alextos.darts.android.common.presentation.ScreenType
import com.alextos.darts.android.common.presentation.rememberScreenType
import com.alextos.darts.android.common.presentation.screens.TabletScreen
import com.alextos.darts.android.common.presentation.views.GameHistoryView
import com.alextos.darts.android.common.presentation.views.GameRecapView
import com.alextos.darts.game.presentation.history.HistoryEvent
import com.alextos.darts.game.presentation.history.HistoryState

@Composable
fun HistoryScreen(
    state: HistoryState,
    onEvent: (HistoryEvent) -> Unit
) {
    when (rememberScreenType()) {
        is ScreenType.Compact -> {
            Scaffold(
                floatingActionButton = {
                    FAB(
                        text = if (state.isRecapVisible) {
                            stringResource(id = R.string.history)
                        } else {
                            stringResource(id = R.string.progress)
                        },
                        icon = if (state.isRecapVisible) {
                            Icons.Filled.List
                        } else {
                            Icons.Filled.ShowChart
                        }
                    ) {
                        if (state.isRecapVisible) {
                            onEvent(HistoryEvent.ShowHistory)
                        } else {
                            onEvent(HistoryEvent.ShowRecap)
                        }
                    }
                }
            ) {
                if (state.isRecapVisible) {
                    GameRecap(state = state) {
                        onEvent(HistoryEvent.ShowHistory)
                    }
                } else {
                    GameHistory(
                        paddingValues = it,
                        state = state,
                        onEvent = onEvent
                    )
                }
            }
        }
        is ScreenType.Large -> {
            BackHandler {
                onEvent(HistoryEvent.BackButtonPressed)
            }

            TabletScreen(
                content1 = {
                    GameHistory(
                        paddingValues = PaddingValues(0.dp),
                        state = state,
                        onEvent = onEvent
                    )
                },
                content2 = {
                    GameRecap(state = state)
                }
            )
        }
    }
}

@Composable
private fun GameHistory(
    paddingValues: PaddingValues,
    state: HistoryState,
    onEvent: (HistoryEvent) -> Unit
) {
    GameHistoryView(
        gameHistory = state.gameHistory,
        currentPage = 0,
        goal = state.gameGoal,
        padding = paddingValues,
        onSelect = { turns, set ->
            onEvent(HistoryEvent.ShowDarts(turns, set))
        }
    )
}

@Composable
private fun GameRecap(state: HistoryState, onBackPressed: (() -> Unit)? = null) {
    GameRecapView(
        history = state.gameHistory,
        averageSets = state.averageTurns(),
        biggestSets = state.biggestTurns(),
        smallestSets = state.smallestTurns(),
        misses = state.misses(),
        overkills = state.overkills(),
        onBackPressed = onBackPressed
    )
}