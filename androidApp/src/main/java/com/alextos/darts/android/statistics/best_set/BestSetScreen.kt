package com.alextos.darts.android.statistics.best_set

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.views.StatisticPlayersListView
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.NoDataView
import com.alextos.darts.statistics.presentation.best_set.BestSetEvent
import com.alextos.darts.statistics.presentation.best_set.BestSetState

@Composable
fun BestSetScreen(
    state: BestSetState,
    onEvent: (BestSetEvent) -> Unit
) {
    Screen(title = stringResource(id = R.string.best_set_statistics)) {
        if (state.playersBestSets.isEmpty()) {
            NoDataView()
        } else {
            StatisticPlayersListView(
                values = state.playersBestSets.map { set ->
                    set.player to set.set.score().toString()
                },
                onValueClick = { index ->
                    onEvent(BestSetEvent.ShowBestSetOfPlayer(state.playersBestSets[index].set))
                }
            )
        }
    }
}