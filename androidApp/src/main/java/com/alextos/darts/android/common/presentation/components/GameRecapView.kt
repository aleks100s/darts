package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.util.numberOfTurns
import com.alextos.darts.game.domain.models.PlayerHistory
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.point.FilledCircularPointDrawer

private val colors = listOf(Color.Yellow, Color.Red, Color.Green, Color.Blue)

@Composable
fun GameRecapView(
    history: List<PlayerHistory>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            RecapLineChart(history = history)
        }
        item {
            RecapLineChartLegend(players = history.map { it.player })
        }
    }
}

@Composable
private fun RecapLineChart(history: List<PlayerHistory>) {
    val maxSize = history.numberOfTurns()
    val data = history.map { playerHistory ->
        LineChartData(
            points = playerHistory.chartData(maxSize).map { value ->
                LineChartData.Point(value, value.toString())
            },
            lineDrawer = SolidLineDrawer(
                color = colors.getOrNull(history.indexOf(playerHistory)) ?: Color.Magenta
            )
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        LineChart(
            linesChartData = data,
            modifier = Modifier.fillMaxSize(0.8f),
            pointDrawer = FilledCircularPointDrawer(),
            horizontalOffset = 5f,
            labels = listOf()
        )
    }
}

@Composable
private fun RecapLineChartLegend(players: List<Player>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        players.forEach {
            ChartLegendItem(text = it.name, color = colors[players.indexOf(it)])
        }
    }
}