package com.alextos.darts.android.game.recap

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.GameRecapView
import com.alextos.darts.game.presentation.history.HistoryState

@Composable
fun RecapScreen(
    historyState: HistoryState,
    onBackPressed: () -> Unit
) {
    Screen(
        title = stringResource(id = R.string.game_recap),
        onBackPressed = onBackPressed
    ) {
        GameRecapView(
            history = historyState.gameHistory,
            averageTurns = historyState.averageTurns(),
            biggestTurns = historyState.biggestTurns(),
            smallestTurns = historyState.smallestTurns(),
            misses = historyState.misses(),
            overkills = historyState.overkills(),
            duration = historyState.duration
        )
    }
}