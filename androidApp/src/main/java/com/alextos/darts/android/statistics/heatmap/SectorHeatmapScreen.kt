package com.alextos.darts.android.statistics.heatmap

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.common.presentation.components.drawSector
import com.alextos.darts.android.common.presentation.extensions.color
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.NoDataView
import com.alextos.darts.statistics.domain.models.SectorHeat
import com.alextos.darts.statistics.domain.models.SectorHeatmapDistribution
import com.alextos.darts.statistics.presentation.heatmap.SectorHeatmapState

@Composable
fun SectorHeatmapScreen(state: SectorHeatmapState) {
    state.distribution?.let { distribution ->
        Screen(title = distribution.player.name) {
            if (distribution.distribution.isEmpty()) {
                NoDataView()
            } else {
                LazyColumn {
                    item {
                        HeatmapDartsDisk(distribution = distribution)
                    }
                }
            }
        }
    } ?: run {
        NoDataView()
    }
}

@Composable
private fun HeatmapDartsDisk(distribution: SectorHeatmapDistribution) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(16.dp)
    ) {
        drawHeatmapMiss()
        drawHeatmapDoubles(distribution.getDoubles())
        drawHeatmapOuters(distribution.getOuters())
        drawHeatmapTriplets(distribution.getTriples())
        drawHeatmapInners(distribution.getInners())
        distribution.getBullseye()?.let {
            drawHeatmapBullseye(it)
        }
        distribution.getDoubleBullseye()?.let {
            drawHeatmapDoubleBullseye(it)
        }
    }
}

private fun DrawScope.drawHeatmapMiss() {
    drawSector(
        multiplier = 1f,
        width = 0.1f,
        color = Color.DarkGray
    )
}

private fun DrawScope.drawHeatmapDoubles(sectors: List<SectorHeat>) {
    drawHeatmapSectorLevel(
        radiusFrom = 0.85f,
        width = 0.1f,
        sectors = sectors
    )
}

private fun DrawScope.drawHeatmapOuters(sectors: List<SectorHeat>) {
    drawHeatmapSectorLevel(
        radiusFrom = 0.55f,
        width = 0.3f,
        sectors = sectors
    )
}

private fun DrawScope.drawHeatmapTriplets(sectors: List<SectorHeat>) {
    drawHeatmapSectorLevel(
        radiusFrom = 0.45f,
        width = 0.1f,
        sectors = sectors
    )
}

private fun DrawScope.drawHeatmapInners(sectors: List<SectorHeat>) {
    drawHeatmapSectorLevel(
        radiusFrom = 0.15f,
        width = 0.3f,
        sectors = sectors
    )
}

private fun DrawScope.drawHeatmapBullseye(sector: SectorHeat) {
    val multiplier = 0.1f
    val width = 0.1f
    val arcRadius = size.width / 2
    val offset = arcRadius * (1 - multiplier)
    drawArc(
        color = sector.color(),
        startAngle = 0f,
        sweepAngle = 360f,
        useCenter = false,
        size = size.times(multiplier),
        style = Stroke(width = arcRadius * width),
        topLeft = Offset(offset, offset)
    )
}

private fun DrawScope.drawHeatmapDoubleBullseye(sector: SectorHeat) {
    val arcRadius = size.width / 2
    drawCircle(
        color = sector.color(),
        radius = arcRadius * 0.05f
    )
}

private fun DrawScope.drawHeatmapSectorLevel(
    radiusFrom: Float,
    width: Float,
    sectors: List<SectorHeat>
) {
    val arcRadius = size.width / 2
    val multiplier = radiusFrom + width / 2
    val offset = arcRadius * (1 - multiplier)
    circleHeatmap(
        sizeMultiplier = multiplier,
        width = arcRadius * width,
        offset = offset,
        sectors = sectors
    )
}

private fun DrawScope.circleHeatmap(
    sizeMultiplier: Float,
    width: Float,
    offset: Float,
    sectors: List<SectorHeat>
) {
    sectors.forEach {
        arcHeatmap(
            sizeMultiplier = sizeMultiplier,
            width = width,
            offset = Offset(offset, offset),
            startAngle = -117f + it.sector.sectorOrder() * 18f,
            color = it.color()
        )
    }
}

private fun DrawScope.arcHeatmap(
    sizeMultiplier: Float,
    width: Float,
    offset: Offset,
    startAngle: Float,
    color: Color
) {
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = 18f,
        useCenter = false,
        size = size.times(sizeMultiplier),
        style = Stroke(width = width),
        topLeft = offset
    )
}