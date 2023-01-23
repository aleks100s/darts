package com.alextos.darts.android.statistics.biggest_final_set

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.StatisticsListView
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.statistics.presentation.biggest_final_set.BiggestFinalSetEvent
import com.alextos.darts.statistics.presentation.biggest_final_set.BiggestFinalSetState

@Composable
fun BiggestFinalSetScreen(
    state: BiggestFinalSetState,
    onEvent: (BiggestFinalSetEvent) -> Unit
) {
    Screen(title = stringResource(id = R.string.biggest_final_set_statistics)) {
        val allPlayersValue = stringResource(id = R.string.biggest_final_set_of_all_players).uppercase() to state.biggestFinalSetOfAll?.score().toString()
        StatisticsListView(
            allPlayersValue = allPlayersValue,
            values = state.playersBiggestFinalSets.map { it.player.name to it.set.score().toString() },
            onAllPlayersValueClick = {
                state.biggestFinalSetOfAll?.let { set ->
                    onEvent(BiggestFinalSetEvent.ShowBiggestFinalSetOfAll(set))
                }
            },
            onValueClick = { index ->
                onEvent(BiggestFinalSetEvent.ShowBiggestFinalSetOfPlayer(state.playersBiggestFinalSets[index].set))
            }
        )
    }
}