package com.alextos.darts.android.common.presentation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.alextos.darts.game.domain.models.Set

@Composable
fun Set.rowColor(): Color {
    return if (isOverkill) {
        MaterialTheme.colors.error
    } else {
        if (leftAfter == 0) {
            MaterialTheme.colors.secondary
        } else {
            Color.Transparent
        }
    }
}