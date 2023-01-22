package com.alextos.darts.android.statistics.most_frequent_shots

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.StatisticsListView
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.statistics.presentation.most_frequent_shots.MostFrequentShotsEvent
import com.alextos.darts.statistics.presentation.most_frequent_shots.MostFrequentShotsState

@Composable
fun MostFrequentShotsScreen(
    state: MostFrequentShotsState,
    onEvent: (MostFrequentShotsEvent) -> Unit
) {
    Screen(title = stringResource(id = R.string.most_frequent_shots_statistics)) {
        val allPlayersValue = stringResource(id = R.string.most_frequent_shots_of_all_players) to state.mostFrequentShots.count().toString()

        StatisticsListView(
            allPlayersValue = allPlayersValue,
            values = state.mostFrequentShots.map { shots ->
                shots.player.name to shots.shots.count().toString()
            },
            onAllPlayersValueClick = {
                if (state.mostFrequentShots.isNotEmpty()) {
                    onEvent(MostFrequentShotsEvent.ShowMostFrequentShotsOfAll(state.mostFrequentShotsOfAll))
                }
            },
            onValueClick = { index ->
                onEvent(MostFrequentShotsEvent.ShowPlayerMostFrequentShots(state.mostFrequentShots[index].shots))
            }
        )
    }
}