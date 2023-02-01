package com.alextos.darts.android.statistics.statistics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.SingleSelectableItem
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.statistics.presentation.statistics.StatisticsEvent

@Composable
fun StatisticsScreen(onEvent: (StatisticsEvent) -> Unit) {
    val events = listOf(
        StatisticsEvent.ShowBestSet to stringResource(id = R.string.best_set_statistics),
        StatisticsEvent.ShowMostFrequentShots to stringResource(id = R.string.most_frequent_shots_statistics),
        StatisticsEvent.ShowBiggestFinalSet to stringResource(id = R.string.biggest_final_set_statistics),
        StatisticsEvent.ShowAverageValues to stringResource(id = R.string.average_values_statistics),
        StatisticsEvent.ShowShotDistribution to stringResource(id = R.string.shot_distribution_statistics),
        StatisticsEvent.ShowVictoryDistribution to stringResource(id = R.string.victory_distribution_statistics)
    )

    Screen(title = stringResource(id = R.string.statistics)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(events) { event ->
                SingleSelectableItem(text = event.second) {
                    onEvent(event.first)
                }
            }
        }
    }
}