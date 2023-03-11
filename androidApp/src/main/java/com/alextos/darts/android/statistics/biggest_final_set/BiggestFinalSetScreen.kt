package com.alextos.darts.android.statistics.biggest_final_set

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.views.StatisticPlayersListView
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.NoDataView
import com.alextos.darts.statistics.presentation.biggest_final_set.BiggestFinalSetEvent
import com.alextos.darts.statistics.presentation.biggest_final_set.BiggestFinalSetState

@Composable
fun BiggestFinalSetScreen(
    state: BiggestFinalSetState,
    onEvent: (BiggestFinalSetEvent) -> Unit
) {
    Screen(title = stringResource(id = R.string.biggest_final_set_statistics)) {
        if (state.playersBiggestFinalSets.isEmpty()) {
            NoDataView()
        } else {
            StatisticPlayersListView(
                values = state.playersBiggestFinalSets.map { it.player to it.set.score().toString() },
                onValueClick = { index ->
                    onEvent(BiggestFinalSetEvent.ShowBiggestFinalSetOfPlayer(state.playersBiggestFinalSets[index].set))
                }
            )
        }
    }
}