package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.alextos.darts.core.domain.Sector

private val hitColor = Color.Blue

@Composable
fun DartsDisk(selectedSector: Sector) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(16.dp)
    ) {
        drawMiss(selectedSector)
        drawDoubles(selectedSector)
        drawOuters(selectedSector)
        drawTriplets(selectedSector)
        drawInners(selectedSector)
        drawBullseye(selectedSector)
        drawDoubleBullseye(selectedSector)
    }
}

fun DrawScope.drawMiss(sector: Sector) {
    drawSector(
        multiplier = 1f,
        width = 0.1f,
        color = if (sector == Sector.Miss) hitColor else Color.Black
    )
}

fun DrawScope.drawDoubles(sector: Sector) {
    drawSectorLevel(
        radiusFrom = 0.85f,
        width = 0.1f,
        firstColor = Color.Green,
        secondColor = Color.Red,
        sector = if (sector.isDouble()) sector else null
    )
}

fun DrawScope.drawOuters(sector: Sector) {
    drawSectorLevel(
        radiusFrom = 0.55f,
        width = 0.3f,
        firstColor = Color.White,
        secondColor = Color.Black,
        sector = if (sector.isOuter()) sector else null
    )
}

fun DrawScope.drawTriplets(sector: Sector) {
    drawSectorLevel(
        radiusFrom = 0.45f,
        width = 0.1f,
        firstColor = Color.Green,
        secondColor = Color.Red,
        sector = if (sector.isTriplet()) sector else null
    )
}

fun DrawScope.drawInners(sector: Sector) {
    drawSectorLevel(
        radiusFrom = 0.15f,
        width = 0.3f,
        firstColor = Color.White,
        secondColor = Color.Black,
        sector = if (sector.isInner()) sector else null
    )
}

fun DrawScope.drawBullseye(sector: Sector) {
    drawSector(
        multiplier = 0.1f,
        width = 0.1f,
        color = if (sector == Sector.SingleBullseye) hitColor else Color.Green
    )
}

fun DrawScope.drawDoubleBullseye(sector: Sector) {
    val arcRadius = size.width / 2
    drawCircle(
        color = if (sector == Sector.DoubleBullseye) hitColor else Color.Red,
        radius = arcRadius * 0.05f
    )
}

fun DrawScope.drawSectorLevel(
    radiusFrom: Float,
    width: Float,
    firstColor: Color,
    secondColor: Color,
    sector: Sector?
) {
    val arcRadius = size.width / 2
    val multiplier = radiusFrom + width / 2
    val offset = arcRadius * (1 - multiplier)
    circle(
        sizeMultiplier = multiplier,
        width = arcRadius * width,
        offset = offset,
        firstColor = firstColor,
        secondColor = secondColor,
        sector = sector
    )
}

fun DrawScope.drawSector(multiplier: Float, width: Float, color: Color, useCenter: Boolean = false) {
    val arcRadius = size.width / 2
    val offset = arcRadius * (1 - multiplier)
    drawArc(
        color = color,
        startAngle = 0f,
        sweepAngle = 360f,
        useCenter = useCenter,
        size = size.times(multiplier),
        style = Stroke(width = arcRadius * width),
        topLeft = Offset(offset, offset)
    )
}

fun DrawScope.circle(
    sizeMultiplier: Float,
    width: Float,
    offset: Float,
    firstColor: Color,
    secondColor: Color,
    sector: Sector?
) {
    (1..20).forEach {
        val isSelected = sector?.sectorOrder() == it
        arc(
            sizeMultiplier = sizeMultiplier,
            width = width,
            offset = Offset(offset, offset),
            startAngle = -117f + it * 18f,
            color = if (isSelected) hitColor else if (it % 2 == 0) firstColor else secondColor
        )
    }
}

fun DrawScope.arc(
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