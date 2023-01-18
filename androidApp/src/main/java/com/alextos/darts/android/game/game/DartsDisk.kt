package com.alextos.darts.android.game.game

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun DartsDisk() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(16.dp)
            .pointerInput(key1 = Unit) {
//                detectTapGestures(
//                    onTap = { offset ->
//                        // end
//                        Log.i("Tag", "Tap at $offset")
//                    },
//                    onPress = { offset ->
//                        // start
//                        Log.i("Tag", "Press at $offset")
//                    }
//                )

                detectDragGestures(
                    onDrag = { drag, _ ->
                        // during
                        Log.i("Tag", "Dragged $drag")
                    }
                )
            }
    ) {
        drawMiss()
        drawDoubles()
        drawOuters()
        drawTriplets()
        drawInners()
        drawBullseye()
        drawDoubleBullseye()
    }
}

fun DrawScope.drawMiss() {
    drawSector(multiplier = 1f, width = 0.1f, color = Color.Black)
}

fun DrawScope.drawDoubles() {
    drawSectorLevel(
        radiusFrom = 0.85f,
        width = 0.1f,
        firstColor = Color.Red,
        secondColor = Color.Green
    )
}

fun DrawScope.drawOuters() {
    drawSectorLevel(
        radiusFrom = 0.55f,
        width = 0.3f,
        firstColor = Color.Black,
        secondColor = Color.White
    )
}

fun DrawScope.drawTriplets() {
    drawSectorLevel(
        radiusFrom = 0.45f,
        width = 0.1f,
        firstColor = Color.Red,
        secondColor = Color.Green
    )
}

fun DrawScope.drawInners() {
    drawSectorLevel(
        radiusFrom = 0.15f,
        width = 0.3f,
        firstColor = Color.Black,
        secondColor = Color.White
    )
}

fun DrawScope.drawBullseye() {
    drawSector(multiplier = 0.1f, width = 0.1f, color = Color.Green)
}

fun DrawScope.drawDoubleBullseye() {
    val arcRadius = size.width / 2
    drawCircle(
        color = Color.Red,
        radius = arcRadius * 0.05f
    )
}

fun DrawScope.drawSectorLevel(
    radiusFrom: Float,
    width: Float,
    firstColor: Color,
    secondColor: Color
) {
    val arcRadius = size.width / 2
    val multiplier = radiusFrom + width / 2
    val offset = arcRadius * (1 - multiplier)
    circle(
        sizeMultiplier = multiplier,
        width = arcRadius * width,
        offset = offset,
        firstColor = firstColor,
        secondColor = secondColor
    )
}

fun DrawScope.drawSector(multiplier: Float, width: Float, color: Color) {
    val arcRadius = size.width / 2
    val offset = arcRadius * (1 - multiplier)
    drawArc(
        color = color,
        startAngle = 0f,
        sweepAngle = 360f,
        useCenter = false,
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
    secondColor: Color
) {
    (1..20).forEach {
        arc(
            sizeMultiplier = sizeMultiplier,
            width = width,
            offset = Offset(offset, offset),
            startAngle = -99f + it * 18f,
            color = if (it % 2 == 0) firstColor else secondColor
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