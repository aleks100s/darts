package com.alextos.darts.android.statistics.shot_distribution

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.statistics.domain.models.PlayerShotDistribution
import com.alextos.darts.statistics.domain.models.ShotDistribution
import com.alextos.darts.statistics.presentation.shot_distribution.ShotDistributionState
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import okhttp3.internal.format

@Composable
fun ShotDistributionScreen(
    state: ShotDistributionState
) {
    Screen(title = stringResource(id = R.string.shot_distribution_statistics)) {
        LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            items(state.playersDistribution) { distribution ->
                //Text(text = "${distribution.distribution.missesPercent() * 100}%")
                PlayerDistributionItem(distribution = distribution)
            }
        }
    }
}

@Composable
private fun PlayerDistributionItem(distribution: PlayerShotDistribution) {
    val missesPercent = distribution.distribution.missesPercent()
    val singlesPercent = distribution.distribution.singlesPercent()
    val doublesPercent = distribution.distribution.doublesPercent()
    val triplesPercent = distribution.distribution.triplesPercent()
    val bullseyePercent = distribution.distribution.bullseyePercent()
    val doubleBullseyePercent = distribution.distribution.doubleBullseyePercent()
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
        Text(text = distribution.player.name, style = MaterialTheme.typography.h3)

        Spacer(modifier = Modifier.height(16.dp))

        PieChart(
            pieChartData = PieChartData(slices),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(1f)
        )

        if (missesPercent > 0) {
            LegendItem(
                color = Color.Gray,
                text = "${missesPercent}% ${stringResource(id = R.string.misses_percent)}"
            )
        }

        if (singlesPercent > 0) {
            LegendItem(
                color = Color.Magenta,
                text = "${singlesPercent}% ${stringResource(id = R.string.singles_percent)}"
            )
        }

        if (doublesPercent > 0) {
            LegendItem(
                color = Color.Cyan,
                text = "${doublesPercent}% ${stringResource(id = R.string.doubles_percent)}"
            )
        }

        if (triplesPercent > 0) {
            LegendItem(
                color = Color.Yellow,
                text = "${triplesPercent}% ${stringResource(id = R.string.triplets_percent)}"
            )
        }

        if (bullseyePercent > 0) {
            LegendItem(
                color = Color.Red,
                text = "${bullseyePercent}% ${stringResource(id = R.string.bullseye_percent)}"
            )
        }

        if (doubleBullseyePercent > 0) {
            LegendItem(
                color = Color.Green,
                text = "${doubleBullseyePercent}% ${stringResource(id = R.string.double_bullseye_percent)}"
            )
        }
    }
}

@Composable
private fun LegendItem(color: Color, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier
            .size(24.dp)
            .background(color))

        Spacer(Modifier.width(8.dp))

        Text(text = text)
    }
}