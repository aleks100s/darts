package com.alextos.darts.android.statistics.best_set

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.DoubleSelectableItem
import com.alextos.darts.statistics.presentation.best_set.BestSetEvent
import com.alextos.darts.statistics.presentation.best_set.BestSetState

@Composable
fun BestSetScreen(
    state: BestSetState,
    onEvent: (BestSetEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        item {
            DoubleSelectableItem(
                leftText = stringResource(id = R.string.best_set_of_all_players).uppercase(),
                rightText = state.bestSetOfAll?.score().toString()
            ) {
                state.bestSetOfAll?.let {
                    onEvent(BestSetEvent.ShowBestSetOfAll(it))
                }
            }
        }

        items(state.playersBestSets) { set ->
            DoubleSelectableItem(
                leftText = set.player.name, rightText = set.set.score().toString()) {
                onEvent(BestSetEvent.ShowBestSetOfPlayer(set.set))
            }
        }
    }
}