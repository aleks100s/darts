package com.alextos.darts.android.game.history

import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.FAB
import com.alextos.darts.android.common.presentation.components.GameHistoryView
import com.alextos.darts.android.common.presentation.components.GameRecapView
import com.alextos.darts.game.presentation.history.HistoryEvent
import com.alextos.darts.game.presentation.history.HistoryState

@Composable
fun HistoryScreen(
    state: HistoryState,
    onEvent: (HistoryEvent) -> Unit
) {
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
                    onEvent(HistoryEvent.ShowProgress)
                }
            }
        }
    ) {
        if (state.isRecapVisible) {
            GameRecapView(history = state.gameHistory)
        } else {
            GameHistoryView(
                gameHistory = state.gameHistory,
                currentPage = 0,
                padding = it,
                onSelect = { turns, set ->
                    onEvent(HistoryEvent.ShowDarts(turns, set))
                }
            )
        }
    }
}