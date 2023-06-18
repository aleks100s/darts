package com.alextos.darts.android.statistics.time

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.TableHeader
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.LoadingView
import com.alextos.darts.android.common.presentation.views.NoDataView
import com.alextos.darts.statistics.domain.models.TimeDuration
import com.alextos.darts.statistics.presentation.time.TimeState

@Composable
fun TimeScreen(
    state: TimeState,
    onBackPressed: () -> Unit
) {
    Screen(
        title = stringResource(id = R.string.time_statistics),
        onBackPressed = onBackPressed
    ) { modifier ->
        if (state.isLoading) {
            LoadingView()
        } else if (state.isEmpty) {
            NoDataView(modifier)
        } else {
            LazyColumn {
                item {
                    TableHeader(columns = listOf(
                        stringResource(id = R.string.player),
                        stringResource(id = R.string.time_statistics)
                    ))
                }

                state.totalTimePlayed?.let {
                    item {
                        Item(name = stringResource(id = R.string.all_players), duration = it)
                    }
                }

                items(state.playersDuration) { duration ->
                    Item(name = duration.player.name, duration = duration.duration)
                }
            }
        }
    }
}

@Composable
private fun Item(name: String, duration: TimeDuration) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = name,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.time_duration, duration.hours, duration.minutes, duration.seconds),
            textAlign = TextAlign.Center
        )
    }
}