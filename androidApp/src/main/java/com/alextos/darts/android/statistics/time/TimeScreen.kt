package com.alextos.darts.android.statistics.time

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
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
            Column {
                state.totalTimePlayed?.let {
                    Item(name = stringResource(id = R.string.all_players), value = it)
                }
            }
        }
    }
}

@Composable
private fun Item(name: String, value: TimeDuration) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(name)

        Text(stringResource(id = R.string.time_duration, value.hours, value.minutes, value.seconds))
    }
}