package com.alextos.darts.android.common.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.alextos.darts.core.domain.model.Turn

@Composable
fun Turn.rowColor(): Color {
    return if (isOverkill) {
        Color.Red.copy(alpha = 0.7f)
    } else {
        if (leftAfter == 0) {
            Color.Green.copy(alpha = 0.7f)
        } else {
            Color.Transparent
        }
    }
}

@Composable
fun Turn.textColor(): Color {
    return if (isOverkill) {
        Color.White
    } else {
        if (leftAfter == 0) {
            Color.Black
        } else {
            Color.Unspecified
        }
    }
}