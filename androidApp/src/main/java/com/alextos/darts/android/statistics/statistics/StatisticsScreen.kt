package com.alextos.darts.android.statistics.statistics

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.RoundedView
import com.alextos.darts.android.common.presentation.components.SingleSelectableItem
import com.alextos.darts.android.common.presentation.extensions.surfaceBackground
import com.alextos.darts.android.common.presentation.screens.Screen

@Composable
fun StatisticsScreen(
    onNavigationEvent: (StatisticsNavigationEvent) -> Unit,
    onBackPressed: () -> Unit
) {
    val events = listOf(
        StatisticsNavigationEvent.ShowBestTurn to stringResource(id = R.string.best_set_statistics),
        StatisticsNavigationEvent.ShowBiggestFinalTurn to stringResource(id = R.string.biggest_final_set_statistics),
        StatisticsNavigationEvent.ShowAverageValues to stringResource(id = R.string.average_values_statistics),
        StatisticsNavigationEvent.ShowShotDistribution to stringResource(id = R.string.shot_distribution_statistics),
        StatisticsNavigationEvent.ShowVictoryDistribution to stringResource(id = R.string.victory_distribution_statistics),
        StatisticsNavigationEvent.ShowSectorHeatmapDistribution to stringResource(id = R.string.sector_heatmap_statistics),
        StatisticsNavigationEvent.ShowTimeStatistics to stringResource(id = R.string.time_statistics)
    )

    Screen(
        title = stringResource(id = R.string.statistics),
        isBackButtonVisible = false,
        onBackPressed = onBackPressed
    ) { modifier ->
        RoundedView(modifier) {
            LazyColumn(modifier = Modifier.surfaceBackground()) {
                items(events) { event ->
                    SingleSelectableItem(text = event.second, showDivider = events.last() != event) {
                        onNavigationEvent(event.first)
                    }
                }
            }
        }
    }
}