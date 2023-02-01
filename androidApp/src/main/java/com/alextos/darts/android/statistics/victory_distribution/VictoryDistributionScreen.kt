package com.alextos.darts.android.statistics.victory_distribution

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
import com.alextos.darts.android.common.presentation.views.NoDataView
import com.alextos.darts.statistics.domain.models.PlayerVictoryDistribution
import com.alextos.darts.statistics.presentation.victory_distribution.VictoryDistributionState
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData

@Composable
fun VictoryDistributionScreen(
    state: VictoryDistributionState
) {
    state.distribution?.let { distribution ->
        Screen(title = distribution.player.name) {
            if (distribution.isEmpty()) {
                NoDataView()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    item {
                        VictoryDistributionChart(distribution = distribution)
                    }
                }
            }
        }
    }
}

@Composable
private fun VictoryDistributionChart(
    distribution: PlayerVictoryDistribution
) {
    val victoryPercent = distribution.victoryPercent()
    val losePercent = distribution.losePercent()
    val slices = listOf(
        PieChartData.Slice(value = victoryPercent, color = Color.Green),
        PieChartData.Slice(value = losePercent, color = Color.Gray)
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
                text = stringResource(id = R.string.games_count, distribution.gamesCount),
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (victoryPercent > 0) {
            ChartLegendItem(
                color = Color.Green,
                text = stringResource(id = R.string.victory_percent, victoryPercent)
            )
        }

        if (losePercent > 0) {
            ChartLegendItem(
                color = Color.Gray,
                text = stringResource(id = R.string.lose_percent, losePercent)
            )
        }
    }
}