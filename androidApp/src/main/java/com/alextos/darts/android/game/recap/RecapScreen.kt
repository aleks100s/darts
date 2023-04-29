package com.alextos.darts.android.game.recap

import androidx.compose.runtime.Composable
import com.alextos.darts.android.common.presentation.views.GameRecapView
import com.alextos.darts.game.presentation.history.HistoryState

@Composable
fun RecapScreen(historyState: HistoryState) {
    GameRecapView(
        history = historyState.gameHistory,
        averageTurns = historyState.averageTurns(),
        biggestTurns = historyState.biggestTurns(),
        smallestTurns = historyState.smallestTurns(),
        misses = historyState.misses(),
        overkills = historyState.overkills()
    )
}