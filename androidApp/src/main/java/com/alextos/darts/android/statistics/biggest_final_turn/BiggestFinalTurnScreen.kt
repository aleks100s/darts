package com.alextos.darts.android.statistics.biggest_final_turn

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.views.StatisticPlayersListView
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.NoDataView
import com.alextos.darts.statistics.presentation.biggest_final_turn.BiggestFinalTurnEvent
import com.alextos.darts.statistics.presentation.biggest_final_turn.BiggestFinalTurnState

@Composable
fun BiggestFinalTurnScreen(
    state: BiggestFinalTurnState,
    onEvent: (BiggestFinalTurnEvent) -> Unit
) {
    Screen(title = stringResource(id = R.string.biggest_final_set_statistics)) {
        if (state.playersBiggestFinalTurns.isEmpty()) {
            NoDataView()
        } else {
            StatisticPlayersListView(
                values = state.playersBiggestFinalTurns.map { it.player to it.set.score().toString() },
                onValueClick = { index ->
                    onEvent(BiggestFinalTurnEvent.ShowBiggestFinalTurnOfPlayer(state.playersBiggestFinalTurns[index].set))
                }
            )
        }
    }
}