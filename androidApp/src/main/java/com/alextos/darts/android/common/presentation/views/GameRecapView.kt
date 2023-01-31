package com.alextos.darts.android.common.presentation.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.ChartLegendItem
import com.alextos.darts.android.common.presentation.components.SectionHeader
import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.util.numberOfTurns
import com.alextos.darts.game.domain.models.PlayerGameValue
import com.alextos.darts.game.domain.models.PlayerHistory
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.point.FilledCircularPointDrawer

private val colors = listOf(Color.Yellow, Color.Red, Color.Green, Color.Blue)

@Composable
fun GameRecapView(
    history: List<PlayerHistory>,
    biggestSets: List<PlayerGameValue>,
    smallestSets: List<PlayerGameValue>,
    misses: List<PlayerGameValue>,
    overkills: List<PlayerGameValue>
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
        item {
            ValuesBlock(values = biggestSets, title = stringResource(id = R.string.biggest_sets))
        }
        item {
            ValuesBlock(values = smallestSets, title = stringResource(id = R.string.smallest_sets))
        }
        item {
            ValuesBlock(values = misses, title = stringResource(id = R.string.misses_count))
        }
        item {
            ValuesBlock(values = overkills, title = stringResource(id = R.string.overkill_count))
        }
        item {
            Spacer(modifier = Modifier.height(72.dp))
        }
    }
}

@Composable
private fun ValuesBlock(values: List<PlayerGameValue>, title: String) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 32.dp)
    ) {
        SectionHeader(title = title)
        values.forEach { set ->
            Item(name = set.player.name, value = set.value)
        }
    }
}

@Composable
private fun Item(name: String, value: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(name)
        Text(value.toString())
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