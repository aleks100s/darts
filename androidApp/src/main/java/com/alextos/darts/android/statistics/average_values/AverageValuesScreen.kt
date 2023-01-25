package com.alextos.darts.android.statistics.average_values

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.statistics.presentation.average_values.AverageValuesState
import okhttp3.internal.format

@Composable
fun AverageValuesScreen(
    state: AverageValuesState
) {
    Screen(title = stringResource(id = R.string.average_values_statistics)) {
        Header()
        LazyColumn {
            item {
                PlayerValues(
                    title = stringResource(id = R.string.all_players),
                    avgSetScore = state.averageSetOfAll,
                    avgShotValue = state.averageShotOfAll
                )
            }

            items(state.playersAverageValues) { value ->
                PlayerValues(
                    title = value.player.name,
                    avgSetScore = value.setScore,
                    avgShotValue = value.shotValue
                )
            }
        }
    }
}

@Composable
private fun PlayerValues(
    title: String,
    avgSetScore: Double,
    avgShotValue: Double
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title
        )
        Text(
            modifier = Modifier.weight(1f),
            text = format("%.2f", avgSetScore)
        )
        Text(
            modifier = Modifier.weight(1f),
            text = format("%.2f", avgShotValue)
        )
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.player)
        )
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.average_set)
        )
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.average_shot)
        )
    }
}