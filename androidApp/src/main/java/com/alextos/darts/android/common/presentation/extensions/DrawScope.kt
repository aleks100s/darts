package com.alextos.darts.android.common.presentation.extensions

import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import kotlin.math.cos
import kotlin.math.sin

fun DrawScope.drawNumbers(numbers: List<Int>) {
    val radius = size.width / 2
    drawContext.canvas.nativeCanvas.apply {
        numbers.forEach { number ->
            val text = "$number"
            val index = numbers.indexOf(number)
            val x = radius * 0.98 * cos(index * Math.PI / 10 - Math.PI / 2) + size.center.x
            val y = radius * 0.98 * sin(index * Math.PI / 10 - Math.PI / 2) + size.center.y
            val paint = Paint()
            paint.color = android.graphics.Color.WHITE
            paint.textSize = radius / 8
            val textRect = Rect()
            paint.getTextBounds(text, 0, text.length, textRect)
            drawText(
                text,
                x.toFloat() - textRect.width() / 2,
                y.toFloat() + textRect.height() / 2,
                paint
            )
        }
    }
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