package com.alextos.darts.android.statistics.heatmap

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.extensions.arc
import com.alextos.darts.android.common.presentation.extensions.color
import com.alextos.darts.android.common.presentation.extensions.drawNumbers
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.NoDataView
import com.alextos.darts.core.domain.model.Sector
import com.alextos.darts.statistics.domain.models.SectorHeat
import com.alextos.darts.statistics.domain.models.SectorHeatmapDistribution
import com.alextos.darts.statistics.presentation.heatmap.SectorHeatmapState

@Composable
fun SectorHeatmapScreen(
    state: SectorHeatmapState,
    onBackPressed: () -> Unit
) {
    Screen(
        title = state.distribution?.player?.name ?: stringResource(id = R.string.no_data_yet),
        onBackPressed = onBackPressed
    ) { modifier ->
        state.distribution?.let { distribution ->
            LazyColumn(modifier = modifier) {
                item {
                    HeatmapDartsDisk(distribution = distribution)
                }

                items(distribution.heatmap) {
                    SectorItem(sectorHeat = it)
                }
            }
        } ?: run {
            NoDataView(modifier = Modifier)
        }
    }
}

@Composable
private fun SectorItem(sectorHeat: SectorHeat) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier
            .size(24.dp)
            .background(sectorHeat.color()))
        Spacer(modifier = Modifier.width(16.dp))
        Text(stringResource(id = R.string.sector_count, sectorHeat.sector.valueString(), sectorHeat.count))
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
        drawHeatmapBullseye(distribution.getBullseye())
        drawHeatmapDoubleBullseye(distribution.getDoubleBullseye())
        drawNumbers(Sector.sectorNumbers)
    }
}

private fun DrawScope.drawHeatmapMiss() {
    val boardRadius = size.width / 2
    drawCircle(
        color = Color.Black,
        radius = boardRadius
    )
}

private fun DrawScope.drawHeatmapDoubles(sectors: List<SectorHeat>) {
    drawHeatmapSectorLevel(
        radiusFrom = 0.75f,
        width = 0.1f,
        sectors = sectors
    )
}

private fun DrawScope.drawHeatmapOuters(sectors: List<SectorHeat>) {
    drawHeatmapSectorLevel(
        radiusFrom = 0.45f,
        width = 0.3f,
        sectors = sectors
    )
}

private fun DrawScope.drawHeatmapTriplets(sectors: List<SectorHeat>) {
    drawHeatmapSectorLevel(
        radiusFrom = 0.35f,
        width = 0.1f,
        sectors = sectors
    )
}

private fun DrawScope.drawHeatmapInners(sectors: List<SectorHeat>) {
    drawHeatmapSectorLevel(
        radiusFrom = 0.1f,
        width = 0.25f,
        sectors = sectors
    )
}

private fun DrawScope.drawHeatmapBullseye(sector: SectorHeat?) {
    val boardRadius = size.width / 2
    drawCircle(
        color = sector?.color() ?: Color.Black,
        radius = boardRadius * 0.1f
    )
}

private fun DrawScope.drawHeatmapDoubleBullseye(sector: SectorHeat?) {
    val boardRadius = size.width / 2
    drawCircle(
        color = sector?.color() ?: Color.Black,
        radius = boardRadius * 0.05f
    )
}

private fun DrawScope.drawHeatmapSectorLevel(
    radiusFrom: Float,
    width: Float,
    sectors: List<SectorHeat>
) {
    val boardRadius = size.width / 2
    val multiplier = radiusFrom + width / 2
    val offset = boardRadius * (1 - multiplier)
    circleHeatmap(
        sizeMultiplier = multiplier,
        width = boardRadius * width,
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
        arc(
            sizeMultiplier = sizeMultiplier,
            width = width,
            offset = Offset(offset, offset),
            startAngle = -117f + it.sector.sectorOrder() * 18f,
            color = it.color()
        )
    }
}