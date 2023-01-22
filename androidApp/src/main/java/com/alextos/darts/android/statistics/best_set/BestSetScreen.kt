package com.alextos.darts.android.statistics.best_set

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.StatisticsListView
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.statistics.presentation.best_set.BestSetEvent
import com.alextos.darts.statistics.presentation.best_set.BestSetState

@Composable
fun BestSetScreen(
    state: BestSetState,
    onEvent: (BestSetEvent) -> Unit
) {
    Screen(title = stringResource(id = R.string.best_set_statistics)) {
        val allPlayersValue = stringResource(id = R.string.best_set_of_all_players).uppercase() to state.bestSetOfAll?.score().toString()

        StatisticsListView(
            allPlayersValue = allPlayersValue,
            values = state.playersBestSets.map { set ->
                set.player.name to set.set.score().toString()
            },
            onAllPlayersValueClick = {
                state.bestSetOfAll?.let {
                    onEvent(BestSetEvent.ShowBestSetOfAll(it))
                }
            },
            onValueClick = { index ->
                onEvent(BestSetEvent.ShowBestSetOfPlayer(state.playersBestSets[index].set))
            }
        )
    }
}