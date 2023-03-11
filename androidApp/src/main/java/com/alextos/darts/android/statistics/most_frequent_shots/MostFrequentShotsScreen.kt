package com.alextos.darts.android.statistics.most_frequent_shots

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.views.StatisticPlayersListView
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.NoDataView
import com.alextos.darts.statistics.presentation.most_frequent_shots.MostFrequentShotsEvent
import com.alextos.darts.statistics.presentation.most_frequent_shots.MostFrequentShotsState

@Composable
fun MostFrequentShotsScreen(
    state: MostFrequentShotsState,
    onEvent: (MostFrequentShotsEvent) -> Unit
) {
    Screen(title = stringResource(id = R.string.most_frequent_shots_statistics)) {
        if (state.mostFrequentShots.isEmpty()) {
            NoDataView()
        } else {
            StatisticPlayersListView(
                values = state.mostFrequentShots.map { shots ->
                    shots.player to ""
                },
                onValueClick = { index ->
                    onEvent(MostFrequentShotsEvent.ShowPlayerMostFrequentShots(state.mostFrequentShots[index].shots))
                }
            )
        }
    }
}