package com.alextos.darts.android.game.history

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.FAB
import com.alextos.darts.android.common.presentation.ScreenType
import com.alextos.darts.android.common.presentation.rememberScreenType
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.screens.SplitScreen
import com.alextos.darts.android.common.presentation.views.GameHistoryView
import com.alextos.darts.android.common.presentation.views.GameRecapView
import com.alextos.darts.android.common.presentation.views.LoadingView
import com.alextos.darts.android.common.presentation.views.NoDataView
import com.alextos.darts.game.presentation.history.HistoryEvent
import com.alextos.darts.game.presentation.history.HistoryState

@Composable
fun HistoryScreen(
    state: HistoryState,
    onEvent: (HistoryEvent) -> Unit,
    onBackPressed: () -> Unit
) {
    when (rememberScreenType()) {
        is ScreenType.Compact -> {
            Scaffold(
                floatingActionButton = {
                    if (!state.isLoading) {
                        FAB(
                            text = stringResource(id = R.string.recap),
                            icon = Icons.Filled.ShowChart
                        ) {
                            onEvent(HistoryEvent.ShowRecap)
                        }
                    }
                }
            ) { paddingValues ->
                Screen(
                    title = stringResource(id = R.string.history),
                    onBackPressed = onBackPressed) { modifier ->
                    ContentLoading(
                        isLoading = state.isLoading,
                        isEmpty = state.gameHistory.isEmpty()
                    ) {
                        GameHistory(
                            modifier = modifier.padding(paddingValues),
                            state = state,
                            onEvent = onEvent
                        )
                    }
                }
            }
        }
        is ScreenType.Large -> {
            SplitScreen(
                title = stringResource(id = R.string.history),
                content1 = {
                    ContentLoading(
                        isLoading = state.isLoading,
                        isEmpty = state.gameHistory.isEmpty()
                    ) {
                        GameHistory(
                            modifier = Modifier,
                            state = state,
                            onEvent = onEvent
                        )
                    }
                },
                content2 = {
                    ContentLoading(
                        isLoading = state.isLoading,
                        isEmpty = state.gameHistory.isEmpty()
                    ) {
                        GameRecap(state = state)
                    }
                },
                onBackPressed = onBackPressed
            )
        }
    }
}

@Composable
private fun ContentLoading(
    isLoading: Boolean,
    isEmpty: Boolean,
    content: @Composable () -> Unit
) {
    if (isLoading) {
        LoadingView()
    } else if (isEmpty) {
        NoDataView(Modifier)
    } else {
        content()
    }
}

@Composable
private fun GameHistory(
    modifier: Modifier,
    state: HistoryState,
    onEvent: (HistoryEvent) -> Unit
) {
    GameHistoryView(
        modifier = modifier,
        gameHistory = state.gameHistory,
        currentPage = 0,
        goal = state.gameGoal,
        onSelect = { turns, page ->
            onEvent(HistoryEvent.ShowDarts(turns, page))
        }
    )
}

@Composable
private fun GameRecap(state: HistoryState) {
    GameRecapView(
        history = state.gameHistory,
        averageTurns = state.averageTurns(),
        biggestTurns = state.biggestTurns(),
        smallestTurns = state.smallestTurns(),
        misses = state.misses(),
        overkills = state.overkills()
    )
}