package com.alextos.darts.android.common.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.alextos.darts.core.domain.model.Sector

@Composable
fun Sector.color(): Color {
    return if (isWhite()) {
        Color.White
    } else if (isBlack()) {
        Color.Black
    } else if (isGreen()) {
        Color.Green
    } else if (isRed()) {
        Color.Red
    } else {
        Color.DarkGray
    }
}

@Composable
fun Sector.textColor(): Color {
    return if (isWhite()) {
        Color.Black
    } else if (isBlack()) {
        Color.White
    } else if (isGreen()) {
        Color.Black
    } else if (isRed()) {
        Color.White
    } else {
        Color.White
    }
}