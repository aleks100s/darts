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
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.common.presentation.extensions.arc
import com.alextos.darts.android.common.presentation.extensions.drawNumbers
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
        drawMiss()
        drawDoubles(selectedSector)
        drawOuters(selectedSector)
        drawTriplets(selectedSector)
        drawInners(selectedSector)
        drawBullseye(selectedSector)
        drawDoubleBullseye(selectedSector)
        drawNumbers(Sector.sectorNumbers)
    }
}

private fun DrawScope.drawMiss() {
    val boardRadius = size.width / 2
    drawCircle(
        color = Color.Black,
        radius = boardRadius
    )
}

private fun DrawScope.drawDoubles(sector: Sector) {
    drawSectorLevel(
        radiusFrom = 0.75f,
        width = 0.1f,
        firstColor = Color.Green,
        secondColor = Color.Red,
        sector = if (sector.isDouble()) sector else null
    )
}

private fun DrawScope.drawOuters(sector: Sector) {
    drawSectorLevel(
        radiusFrom = 0.45f,
        width = 0.3f,
        firstColor = Color.White,
        secondColor = Color.Black,
        sector = if (sector.isOuter()) sector else null
    )
}

private fun DrawScope.drawTriplets(sector: Sector) {
    drawSectorLevel(
        radiusFrom = 0.35f,
        width = 0.1f,
        firstColor = Color.Green,
        secondColor = Color.Red,
        sector = if (sector.isTriplet()) sector else null
    )
}

private fun DrawScope.drawInners(sector: Sector) {
    drawSectorLevel(
        radiusFrom = 0.1f,
        width = 0.25f,
        firstColor = Color.White,
        secondColor = Color.Black,
        sector = if (sector.isInner()) sector else null
    )
}

private fun DrawScope.drawBullseye(sector: Sector) {
    val boardRadius = size.width / 2
    drawCircle(
        color = if (sector == Sector.SingleBullseye) hitColor else Color.Green,
        radius = boardRadius * 0.1f
    )
}

private fun DrawScope.drawDoubleBullseye(sector: Sector) {
    val boardRadius = size.width / 2
    drawCircle(
        color = if (sector == Sector.DoubleBullseye) hitColor else Color.Red,
        radius = boardRadius * 0.05f
    )
}

private fun DrawScope.drawSectorLevel(
    radiusFrom: Float,
    width: Float,
    firstColor: Color,
    secondColor: Color,
    sector: Sector?
) {
    val boardRadius = size.width / 2
    val multiplier = radiusFrom + width / 2
    val offset = boardRadius * (1 - multiplier)
    circle(
        sizeMultiplier = multiplier,
        width = boardRadius * width,
        offset = offset,
        firstColor = firstColor,
        secondColor = secondColor,
        sector = sector
    )
}

private fun DrawScope.circle(
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