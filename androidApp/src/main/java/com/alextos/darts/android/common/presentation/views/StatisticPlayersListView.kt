package com.alextos.darts.android.common.presentation.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alextos.darts.android.common.presentation.components.PlayerDisclosureItem
import com.alextos.darts.core.domain.Player

@Composable
fun StatisticPlayersListView(
    values: List<Pair<Player, String>>,
    onValueClick: (Int) -> Unit
) {
    if (values.isEmpty()) {
        NoDataView()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(values) { value ->
                PlayerDisclosureItem(player = value.first, rightText = value.second) {
                    onValueClick(values.indexOf(value))
                }
            }
        }
    }
}