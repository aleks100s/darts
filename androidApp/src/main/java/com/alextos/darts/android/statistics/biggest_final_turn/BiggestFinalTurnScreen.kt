package com.alextos.darts.android.statistics.biggest_final_turn

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.views.StatisticPlayersListView
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.LoadingView
import com.alextos.darts.android.common.presentation.views.NoDataView
import com.alextos.darts.statistics.presentation.biggest_final_turn.BiggestFinalTurnState

@Composable
fun BiggestFinalTurnScreen(
    state: BiggestFinalTurnState,
    onNavigationEvent: (BiggestFinalTurnNavigationEvent) -> Unit,
    onBackPressed: () -> Unit
) {
    Screen(
        title = stringResource(id = R.string.biggest_final_set_statistics),
        onBackPressed = onBackPressed
    ) { modifier ->
        if (state.isLoading) {
            LoadingView()
        } else if (state.playersBiggestFinalTurns.isEmpty()) {
            NoDataView(modifier)
        } else {
            StatisticPlayersListView(
                modifier,
                values = state.playersBiggestFinalTurns.map { it.player to it.set.score().toString() },
                onValueClick = { index ->
                    onNavigationEvent(BiggestFinalTurnNavigationEvent.ShowBiggestFinalTurnNavigationOfPlayer(state.playersBiggestFinalTurns[index].set))
                }
            )
        }
    }
}