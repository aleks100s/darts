package com.alextos.darts.android.statistics.shot_distribution

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.ChartLegendItem
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.LoadingView
import com.alextos.darts.android.common.presentation.views.NoDataView
import com.alextos.darts.statistics.domain.models.ShotDistribution
import com.alextos.darts.statistics.presentation.shot_distribution.ShotDistributionState
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData

@Composable
fun ShotDistributionScreen(
    state: ShotDistributionState,
    onBackPressed: () -> Unit
) {
    Screen(
        title = state.distribution?.player?.name ?: stringResource(id = R.string.statistics),
        onBackPressed = onBackPressed
    ) { modifier ->
        if (state.isLoading) {
            LoadingView()
        } else {
            state.distribution?.let { distribution ->
                if (distribution.distribution.isEmpty()) {
                    NoDataView(modifier)
                } else {
                    LazyColumn(
                        modifier = modifier.fillMaxSize(),
                    ) {
                        item {
                            PlayerDistributionItem(distribution = distribution.distribution)
                        }
                    }
                }
            } ?: run {
                NoDataView(modifier = modifier)
            }
        }
    }
}

@Composable
private fun PlayerDistributionItem(distribution: ShotDistribution) {
    val missesPercent = distribution.missesPercent()
    val singlesPercent = distribution.singlesPercent()
    val doublesPercent = distribution.doublesPercent()
    val triplesPercent = distribution.triplesPercent()
    val bullseyePercent = distribution.bullseyePercent()
    val doubleBullseyePercent = distribution.doubleBullseyePercent()
    val slices = listOf(
        PieChartData.Slice(value = missesPercent, color = Color.Gray),
        PieChartData.Slice(value = singlesPercent, color = Color.Magenta),
        PieChartData.Slice(value = doublesPercent, color = Color.Cyan),
        PieChartData.Slice(value = triplesPercent, color = Color.Yellow),
        PieChartData.Slice(value = bullseyePercent, color = Color.Red),
        PieChartData.Slice(value = doubleBullseyePercent, color = Color.Green)
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 24.dp)
    ) {
        val configuration = LocalConfiguration.current
        Box(
            modifier = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Modifier
                    .size(300.dp)
            } else {
                Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(1f)
            },
            contentAlignment = Alignment.Center
        ) {
            PieChart(
                pieChartData = PieChartData(slices),
                modifier = Modifier.fillMaxSize()
            )

            Text(
                text = stringResource(id = R.string.throws_count, distribution.totalCount()),
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (missesPercent > 0) {
            ChartLegendItem(
                color = Color.Gray,
                text = stringResource(id = R.string.misses_percent, missesPercent)
            )
        }

        if (singlesPercent > 0) {
            ChartLegendItem(
                color = Color.Magenta,
                text = stringResource(id = R.string.singles_percent, singlesPercent)
            )
        }

        if (doublesPercent > 0) {
            ChartLegendItem(
                color = Color.Cyan,
                text = stringResource(id = R.string.doubles_percent, doublesPercent)
            )
        }

        if (triplesPercent > 0) {
            ChartLegendItem(
                color = Color.Yellow,
                text = stringResource(id = R.string.triplets_percent, triplesPercent)
            )
        }

        if (bullseyePercent > 0) {
            ChartLegendItem(
                color = Color.Red,
                text = stringResource(id = R.string.bullseye_percent, bullseyePercent)
            )
        }

        if (doubleBullseyePercent > 0) {
            ChartLegendItem(
                color = Color.Green,
                text = stringResource(id = R.string.double_bullseye_percent, doubleBullseyePercent)
            )
        }
    }
}