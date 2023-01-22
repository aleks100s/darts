package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatisticsListView(
    allPlayersValue: Pair<String, String>,
    values: List<Pair<String, String>>,
    onAllPlayersValueClick: () -> Unit,
    onValueClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
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
            DoubleSelectableItem(
                leftText = value.first, rightText = value.second) {
                onValueClick(values.indexOf(value))
            }
        }
    }
}