package com.alextos.darts.android.statistics.best_turn

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.views.StatisticPlayersListView
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.NoDataView
import com.alextos.darts.statistics.presentation.best_set.BestTurnEvent
import com.alextos.darts.statistics.presentation.best_set.BestTurnState

@Composable
fun BestTurnScreen(
    state: BestTurnState,
    onEvent: (BestTurnEvent) -> Unit
) {
    Screen(title = stringResource(id = R.string.best_set_statistics)) {
        if (state.playersBestTurns.isEmpty()) {
            NoDataView()
        } else {
            StatisticPlayersListView(
                values = state.playersBestTurns.map { set ->
                    set.player to set.set.score().toString()
                },
                onValueClick = { index ->
                    onEvent(BestTurnEvent.ShowBestTurnOfPlayer(state.playersBestTurns[index].set))
                }
            )
        }
    }
}