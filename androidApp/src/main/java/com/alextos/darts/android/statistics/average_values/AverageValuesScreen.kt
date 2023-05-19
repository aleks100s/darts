package com.alextos.darts.android.statistics.average_values

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.LoadingView
import com.alextos.darts.android.common.presentation.views.NoDataView
import com.alextos.darts.statistics.presentation.average_values.AverageValuesState

@Composable
fun AverageValuesScreen(
    state: AverageValuesState,
    onBackPressed: () -> Unit
) {
    Screen(
        title = stringResource(id = R.string.average_values_statistics),
        onBackPressed = onBackPressed
    ) { modifier ->
        if (state.isLoading) {
            LoadingView()
        } else if (state.isEmpty()) {
            NoDataView(modifier)
        } else {
            LazyColumn(modifier) {
                item {
                    Header()
                }

                item {
                    PlayerValues(
                        title = stringResource(id = R.string.all_players),
                        avgSetScore = state.averageTurnOfAll,
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
}

@Composable
private fun PlayerValues(
    title: String,
    avgSetScore: Double,
    avgShotValue: Double
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(1f),
            text = String.format("%.2f", avgSetScore),
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(1f),
            text = String.format("%.2f", avgShotValue),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.player),
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.average_set),
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.average_shot),
            textAlign = TextAlign.Center
        )
    }
}