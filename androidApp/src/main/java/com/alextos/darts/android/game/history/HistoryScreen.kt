package com.alextos.darts.android.game.history

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.alextos.darts.android.common.presentation.components.GameHistoryView
import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.game.presentation.history.HistoryEvent

@Composable
fun HistoryScreen(
    history: List<PlayerHistory>,
    onEvent: (HistoryEvent) -> Unit
) {
    Scaffold {
        GameHistoryView(
            gameHistory = history,
            currentPage = 0,
            padding = it,
            onSelect = { turns, set ->
                onEvent(HistoryEvent.ShowDarts(turns, set))
            }
        )
    }
}