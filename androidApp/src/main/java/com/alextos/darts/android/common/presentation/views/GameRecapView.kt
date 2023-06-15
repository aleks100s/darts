package com.alextos.darts.android.common.presentation.views

import androidx.compose.foundation.background
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
import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.core.util.numberOfTurns
import com.alextos.darts.game.domain.models.GameDuration
import com.alextos.darts.game.domain.models.PlayerGameValue
import com.alextos.darts.game.domain.models.PlayerHistory
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.point.FilledCircularPointDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation

private val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Magenta, Color.Cyan)

@Composable
fun GameRecapView(
    history: List<PlayerHistory>,
    averageTurns: List<PlayerGameValue>,
    biggestTurns: List<PlayerGameValue>,
    smallestTurns: List<PlayerGameValue>,
    misses: List<PlayerGameValue>,
    overkills: List<PlayerGameValue>,
    duration: GameDuration,
    numberOfTurns: Int
) {
    if (history.isNotEmpty()) {
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
            if (!duration.isEmpty) {
                item {
                    GameDuration(duration = duration)
                }
            }

            item {
                NumberOfTurns(numberOfTurns = numberOfTurns)
            }

            item {
                ValuesBlock(values = averageTurns, title = stringResource(id = R.string.average_set_recap))
            }
            item {
                ValuesBlock(values = biggestTurns, title = stringResource(id = R.string.biggest_sets))
            }
            item {
                ValuesBlock(values = smallestTurns, title = stringResource(id = R.string.smallest_sets))
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
                color = colors.getOrNull(history.indexOf(playerHistory)) ?: Color.DarkGray
            ),
            startAtZero = true
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        LineChart(
            linesChartData = data,
            modifier = Modifier
                .fillMaxSize(0.8f)
                .aspectRatio(1f),
            pointDrawer = FilledCircularPointDrawer(),
            horizontalOffset = 5f,
            labels = listOf(),
            animation = simpleChartAnimation()
        )
    }
}

@Composable
private fun RecapLineChartLegend(players: List<Player>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        players.forEach {
            val color = colors.getOrNull(players.indexOf(it)) ?: Color.DarkGray
            ChartLegendItem(text = it.name, color = color)
        }
    }
}

@Composable
private fun GameDuration(duration: GameDuration) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text(stringResource(id = R.string.game_duration, duration.minutes, duration.seconds))
    }
}

@Composable
private fun NumberOfTurns(numberOfTurns: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text(stringResource(id = R.string.number_of_turns, numberOfTurns))
    }
}