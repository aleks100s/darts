package com.alextos.darts.android.common.presentation.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alextos.darts.android.common.presentation.components.DoubleSelectableItem
import com.alextos.darts.android.common.presentation.components.PlayerDisclosureItem
import com.alextos.darts.core.domain.Player

@Composable
fun StatisticPlayersListView(
    allPlayersValue: Pair<String, String>?,
    values: List<Pair<Player, String>>,
    onAllPlayersValueClick: () -> Unit,
    onValueClick: (Int) -> Unit
) {
    if (allPlayersValue == null) {
        NoDataView()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            allPlayersValue?.let { allPlayersValue ->
                item {
                    DoubleSelectableItem(
                        leftText = allPlayersValue.first,
                        rightText = allPlayersValue.second
                    ) {
                        onAllPlayersValueClick()
                    }
                }
            }

            items(values) { value ->
                PlayerDisclosureItem(player = value.first, rightText = value.second) {
                    onValueClick(values.indexOf(value))
                }
            }
        }
    }
}