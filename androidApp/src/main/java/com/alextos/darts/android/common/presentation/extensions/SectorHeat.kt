package com.alextos.darts.android.common.presentation.extensions

import androidx.compose.ui.graphics.Color
import com.alextos.darts.statistics.domain.models.SectorHeat

fun SectorHeat.color(): Color {
    return Color(heat, heat / 3, heat / 3)
}