package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.core.util.numberOfTurns
import com.alextos.darts.game.domain.models.PlayerHistory
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.point.FilledCircularPointDrawer

@Composable
fun GameProgressView(
    history: List<PlayerHistory>
) {
    val maxSize = history.numberOfTurns()
    val colors = listOf(Color.Yellow, Color.Red, Color.Green, Color.Blue)
    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.surface),
        contentAlignment = Alignment.Center
    ) {
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
        LineChart(
            linesChartData = data,
            modifier = Modifier.fillMaxSize(0.8f),
            pointDrawer = FilledCircularPointDrawer(),
            horizontalOffset = 5f,
            labels = (1..history.numberOfTurns()).map {
                stringResource(id = R.string.throw_number, it)
            }
        )
    }
}