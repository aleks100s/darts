package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alextos.darts.core.domain.Player

@Composable
fun StatisticsListView(
    allPlayersValue: Pair<String, String>,
    values: List<Pair<Player, String>>,
    onAllPlayersValueClick: () -> Unit,
    onValueClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            DoubleSelectableItem(
                leftText = allPlayersValue.first,
                rightText = allPlayersValue.second
            ) {
                onAllPlayersValueClick()
            }
        }

        items(values) { value ->
            PlayerDisclosureItem(player = value.first, rightText = value.second) {
                onValueClick(values.indexOf(value))
            }
        }
    }
}